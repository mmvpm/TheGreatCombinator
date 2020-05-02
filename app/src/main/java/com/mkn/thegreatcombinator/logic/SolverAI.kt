package com.mkn.thegreatcombinator.logic

class SolverAI(private val length: Int = 4,
               private val maxDigit: Int = 6) : ISolver {

    private var lastAttempt: String = ""
    private var lastResponse: Pair<Int, Int> = Pair(0, 0)

    private val possibleAnswers: MutableSet<String> = mutableSetOf()


    override fun makeAttempt(): String {
        if (possibleAnswers.isEmpty()) {
            lastAttempt = makeFirstAttempt()
            return lastAttempt
        }

        var best: String = possibleAnswers.first()
        possibleAnswers.forEach {
            if (countDifference(it) < countDifference(best)) {
                best = it
            }
        }

        lastAttempt = best
        return lastAttempt
    }

    override fun parseResponse(response: Pair<Int, Int>) {
        lastResponse = response

        possibleAnswers.removeIf {
            checkAttempt(lastAttempt, it, length) != lastResponse
        }

        if (possibleAnswers.isEmpty()) {
            generatePossible()
        }
    }

    private fun makeFirstAttempt(): String {
        val partsCount = 3
        val bound: Int = length / partsCount

        val digits: MutableSet<Int> = mutableSetOf()
        while (digits.size < partsCount) {
            digits.add(randomDigit(maxDigit))
        }

        val attempt = StringBuilder(length)
        digits.forEach {
            attempt.append(it.toString().repeat(bound))
        }
        attempt.append(digits.last().toString().repeat(length % partsCount))

        return attempt.toString()
    }

    private fun countDifference(attempt: String):Int {
        val (aCount, bCount) = checkAttempt(attempt, lastAttempt, length)
        val addition: Int = if (attempt.toSet().size >= 3) -1 else 0
        return aCount + bCount + addition
    }

    private fun generatePossible(prefix: String = "") {
        if (prefix.length == length) {
            if (checkAttempt(lastAttempt, prefix, length) == lastResponse) {
                possibleAnswers.add(prefix)
            }
            return
        }

        for (i in 1..maxDigit) {
            generatePossible(prefix + i.toString())
        }
    }

}