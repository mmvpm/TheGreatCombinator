class SolverAI(override val length: Int = 4,
               override val maxDigit: Int = 6) : Solver {

    private lateinit var lastAttempt: String
    private lateinit var lastResponse: Pair<Int, Int>

    private var possibleAnswers: Set<String> = setOf()


    private fun makeFirstAttempt(): String {
        val partsCount = 3
        val bound: Int = length / partsCount

        var digits: Set<Int> = setOf()
        while (digits.size < partsCount) {
            digits = digits.plus(utility.randomDigit(maxDigit))
        }

        val attempt = StringBuilder(length)
        digits.forEach {
            attempt.append(it.toString().repeat(bound))
        }
        attempt.append(digits.last().toString().repeat(length % partsCount))

        return attempt.toString()
    }

    private fun countDifference(first: String, second: String): Int {
        return utility.check(first, second, length).toList().sum()
    }

    override fun makeAttempt(): String {
        if (possibleAnswers.isEmpty()) {
            lastAttempt = makeFirstAttempt()
            return lastAttempt
        }

        var best: String = possibleAnswers.first()
        possibleAnswers.forEach {
            if (countDifference(best, lastAttempt) < countDifference(it, lastAttempt)) {
                best = it
            }
        }

        lastAttempt = best
        return lastAttempt
    }


    private fun generatePossible(prefix: String = "") {
        if (prefix.length == length) {
            if (utility.check(lastAttempt, prefix, length) == lastResponse) {
                possibleAnswers = possibleAnswers.plus(prefix)
            }
            return
        }

        for (i in 1..maxDigit) {
            generatePossible(prefix + i.toString())
        }
    }

    override fun parseResponse(response: Pair<Int, Int>) {
        lastResponse = response

        var toRemove: List<String> = mutableListOf()
        possibleAnswers.forEach {
            if (utility.check(lastAttempt, it, length) != lastResponse) {
                toRemove = toRemove.plus(it)
            }
        }
        toRemove.forEach {
            possibleAnswers = possibleAnswers.minus(it)
        }

        if (possibleAnswers.isEmpty()) {
            generatePossible()
        }

        println("// Осталось вариантов: ${possibleAnswers.size}")
    }

}