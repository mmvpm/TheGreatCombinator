package com.mkn.thegreatcombinator.logic

class SolverAI(private val length: Int = 4,
               private val maxDigit: Int = 6) : ISolver {

    // Последняя сделанная попытка и ответ на неё
    private var lastAttempt: String = ""
    private var lastResponse: Pair<Int, Int> = Pair(0, 0)
    // Множество вероятных ответов
    private val possibleAnswers: MutableSet<String> = mutableSetOf()


    fun getLastAttempt(): String = lastAttempt

    override fun makeAttempt(): String {
        // При первом запуске
        if (possibleAnswers.isEmpty()) {
            lastAttempt = makeFirstAttempt()
            return lastAttempt
        }

        // Минимизируем похожесть двух последовательных попыток
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

        // Исключение неверные ответов
        possibleAnswers.removeIf {
            checkAttempt(lastAttempt, it, length) != lastResponse
        }

        // При первом запуске
        if (possibleAnswers.isEmpty()) {
            generatePossible()
        }
    }

    // Пользователь нажал Заново/Сдаться
    fun restart() {
        lastAttempt = ""
        lastResponse = Pair(0, 0)
        possibleAnswers.clear()
    }

    private fun makeFirstAttempt(): String {
        // Число состоит из 3-ёх частей: aabbccc
        val partsCount = 3
        // Размер частей, кроме (может быть) последней
        val bound: Int = length / partsCount

        // Неообходимы неповторяющиеся(!) цифры для каждой части
        val digits: MutableSet<Int> = mutableSetOf()
        while (digits.size < partsCount) {
            digits.add(randomDigit(maxDigit))
        }

        // Первая попытка
        val attempt = StringBuilder(length)
        digits.forEach {
            attempt.append(it.toString().repeat(bound))
        }
        // Последняя часть может быть чуть больше остальных
        attempt.append(digits.last().toString().repeat(length % partsCount))

        return attempt.toString()
    }

    private fun countDifference(attempt: String): Int {
        // Похожесть двух попыток
        val (aCount, bCount) = checkAttempt(attempt, lastAttempt, length)

        // Количество различных цифр в попытке
        val addition: Int = if (attempt.toSet().size >= 3) -1 else 0

        return aCount + bCount + addition
    }

    private fun generatePossible(prefix: String = "") {
        if (prefix.length == length) {
            // Отсечение заранее неподходящих (оптимизация памяти)
            if (checkAttempt(lastAttempt, prefix, length) == lastResponse) {
                possibleAnswers.add(prefix)
            }
            return
        }
        // Рекурсивный перебор вариантов
        for (i in 1..maxDigit) {
            generatePossible(prefix.plus(i))
        }
    }

}