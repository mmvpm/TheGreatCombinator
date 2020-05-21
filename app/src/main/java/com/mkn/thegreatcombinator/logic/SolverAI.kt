package com.mkn.thegreatcombinator.logic

import kotlin.math.min

class SolverAI(private val length: Int = 4,
               private val maxDigit: Int = 6) : ISolver {

    // Последняя сделанная попытка и ответ на неё
    private var lastAttempt: String = ""
    private var lastResponse: Pair<Int, Int> = Pair(0, 0)

    // Множество всех сделанных попыток
    private val allAttempts: MutableSet<String> = mutableSetOf()
    // Множество вероятных ответов
    private val possibleAnswers: MutableSet<String> = mutableSetOf()


    fun getLastAttempt(): String = lastAttempt

    override fun makeAttempt(): String {
        lastAttempt = when (possibleAnswers.size) {
            0 -> firstChoice()
            in 1..99 -> optimalChoice() // Медленный алгоритм
            else -> heuristicChoice()
        }
        allAttempts.add(lastAttempt)
        return lastAttempt
    }

    override fun parseResponse(response: Pair<Int, Int>) {
        lastResponse = response

        // При первом запуске
        if (possibleAnswers.isEmpty()) {
            generatePossible()
            return
        }

        // Исключение неверных ответов
        possibleAnswers.removeIf {
            checkAttempt(lastAttempt, it) != lastResponse
        }
    }

    // Пользователь нажал Заново/Сдаться
    fun restart() {
        lastAttempt = ""
        lastResponse = Pair(0, 0)
        possibleAnswers.clear()
    }

    // Количество оставшихся вариантов ответа
    fun leftAnswers(): Int {
        return if (possibleAnswers.size == 0)
            Int.MAX_VALUE
        else
            possibleAnswers.size
    }


    // Первая попытка (когда ещё ничего не известно про загаданное число)
    private fun firstChoice(): String {
        // Число состоит из 3-ёх частей: aabbccc
        val partsCount = min(maxDigit, 3)
        // Размер частей, кроме (может быть) последней
        val bound: Int = length / partsCount

        // Необходимы неповторяющиеся(!) цифры для каждой части
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

    // Выбор следующей попытки на основании некоторой эвристики
    private fun heuristicChoice(): String {
        // Немного рандома
        val magicSpeedUp = possibleAnswers.toList().shuffled()

        // Минимизируем похожесть двух последовательных попыток
        var best: String = magicSpeedUp.first()
        magicSpeedUp.forEach {
            if (countDifference(it) < countDifference(best)) {
                best = it
            }
        }
        return best
    }

    // Выбор следующей попытки на основании более оптимальной эвристики
    private fun optimalChoice(): String {
        // Лучший (на текущий момент) выбор
        var best = ""
        var bestSize = Double.MAX_VALUE

        // Перебор возможных попыток
        for (attempt in possibleAnswers) {
            val count = Array(length + 1) { IntArray(length + 1) }

            for (answer in possibleAnswers) {
                val (aCount, bCount) = checkAttempt(attempt, answer)
                count[aCount][bCount] += 1
            }

            // Средний размер пространства ответов
            var average = 0.0
            count.forEach { it.forEach { x -> average += x * x } }
            average /= possibleAnswers.size

            // Минимизируем размер следующего множества возможных ответов
            if (average < bestSize) {
                best = attempt
                bestSize = average
            }
        }
        return best
    }

    private fun countDifference(attempt: String): Int {
        // Похожесть двух попыток
        val (aCount, bCount) = checkAttempt(attempt, lastAttempt)
        val addition: Int = if (attempt.toSet().size >= 3) -1 else 0
        return aCount * 2 + bCount + addition
    }

    private fun generatePossible(prefix: String = "") {
        if (prefix.length == length) {
            // Отсечение заранее неподходящих (оптимизация памяти)
            if (checkAttempt(lastAttempt, prefix) == lastResponse && prefix !in allAttempts) {
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