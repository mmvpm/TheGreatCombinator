class SolverAI(override val length: Int = 4,
               override val maxDigit: Int = 6) : ISolver {

    private lateinit var lastAttempt: String
    private lateinit var lastResponse: Pair<Int, Int>

    private val possibleAnswers: MutableSet<String> = mutableSetOf()


    private fun makeFirstAttempt(): String {
        val partsCount = 3
        val bound: Int = length / partsCount

        var digits: Set<Int> = setOf()
        while (digits.size < partsCount) {
            digits = digits.plus(Utility.randomDigit(maxDigit))
        }

        val attempt = StringBuilder(length)
        digits.forEach {
            attempt.append(it.toString().repeat(bound))
        }
        attempt.append(digits.last().toString().repeat(length % partsCount))

        return attempt.toString()
    }

    private fun countDifference(attempt: String):Int {
        val response: Pair<Int, Int> = Utility.check(attempt, lastAttempt, length)
        val addition: Int = if (attempt.toSet().size >= 3) -1 else 0
        return response.toList().sum() + addition
    }

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


    private fun generatePossible(prefix: String = "") {
        if (prefix.length == length) {
            if (Utility.check(lastAttempt, prefix, length) == lastResponse) {
                possibleAnswers.add(prefix)
            }
            return
        }

        for (i in 1..maxDigit) {
            generatePossible(prefix + i.toString())
        }
    }

    override fun parseResponse(response: Pair<Int, Int>) {
        lastResponse = response

        val toRemove: MutableList<String> = mutableListOf()
        possibleAnswers.forEach {
            if (Utility.check(lastAttempt, it, length) != lastResponse) {
                toRemove.add(it)
            }
        }
        toRemove.forEach {
            possibleAnswers.remove(it)
        }

        if (possibleAnswers.isEmpty()) {
            generatePossible()
        }
    }

}