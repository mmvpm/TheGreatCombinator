package com.mkn.thegreatcombinator.logic

import kotlin.math.min
import kotlin.math.max
import kotlin.math.pow

class SolverAI(private val length: Int = 4,
               private val maxDigit: Int = 6) : ISolver {

    // Последняя сделанная попытка и ответ на неё
    private var lastAttempt: String = ""
    private var lastResponse: Pair<Int, Int> = Pair(0, 0)

    // Множество всех сделанных попыток и ответов на них
    private val allAttempts: MutableList<String> = mutableListOf()
    private val allResponses: MutableList<Pair<Int, Int>> = mutableListOf()
    // Требуется для отсечения лишних вариантов в generatePossible()
    private val maxNumberOfB: MutableList<IntArray> = mutableListOf()

    // Множество вероятных ответов
    private val possibleAnswers: MutableSet<String> = mutableSetOf()

    // Ограничители времени работы
    private val timeBound = 100
    private val timeBoundNext = timeBound * timeBound
    private val sourceSize = maxDigit.toDouble().pow(length).toInt()

    // Предполагаются естественные условия: (1 <= length <= 8) и (2 <= maxDigit <= 9)
    // Для разных length и maxDigit требуются разные данные для ускорения алгоритма
    //                        maxDigit =  4  5  6  7  8  9   // length
    private val magicList = listOf(listOf(0, 0, 0, 0, 1, 1), // 5
                                   listOf(0, 0, 1, 1, 2, 2), // 6
                                   listOf(0, 1, 2, 3, 4, 4), // 7
                                   listOf(1, 2, 4, 5, 6, 7)) // 8
    // Количество вызовов randomChoice() (быстро работает на большом объёме данных)
    private var randomRuns = if (sourceSize > timeBoundNext)
        magicList[length - 5][maxDigit - 4]
    else
        0


    fun getLastAttempt(): String = lastAttempt

    override fun makeAttempt(): String {
        // Выбор алгоритма в зависимоти от размера множества ответов
        lastAttempt = if (allAttempts.size < randomRuns) {
            randomChoice()
        }
        else when (possibleAnswers.size) {
            0 -> firstChoice()
            in 1..timeBound -> optimalChoice()
            else -> heuristicChoice()
        }
        allAttempts.add(lastAttempt)

        // Количество каждой из цифр в lastAttempt
        val prefSum = MutableList(max(length, maxDigit) + 1) {0}
        lastAttempt.forEach { prefSum[it - '0'] += 1 }
        prefSum.sortDescending()
        for (i in 1 until prefSum.size) {
            prefSum[i] += prefSum[i - 1]
        }
        prefSum.add(0, 0)
        // prefSum[i] - максимально возможное количество "B" (в response),
        // которое можно получить, используя ровно i позиций в попытке
        maxNumberOfB.add(prefSum.toIntArray())

        return lastAttempt
    }

    override fun parseResponse(response: Pair<Int, Int>) {
        lastResponse = response
        allResponses.add(response)

        // Пока запускается randomChoice(), которому не требуется possibleAnswers
        if (allAttempts.size < randomRuns) {
            return
        }
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
        allAttempts.clear()
        allResponses.clear()
        maxNumberOfB.clear()
    }

    // Количество оставшихся вариантов ответа (или +inf)
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

    // Выбор следующей попытки, если вариантов слишком много
    private fun randomChoice(): String {
        if (possibleAnswers.isNotEmpty()) {
            return possibleAnswers.random()
        }
        if (lastAttempt == "") {
            return firstChoice()
        }

        var countBound = 0
        var attempt: String = lastAttempt
        // Пытаемся выбрать по-умному
        while (!checkAllConditions(attempt) && countBound < timeBoundNext) {
            attempt = MutableList(length) {
                randomDigit(maxDigit)
            }.joinToString("")
            countBound += 1
        }
        // Если так и не нашли, то берём хоть что-нибудь
        while (attempt in allAttempts) {
            attempt = MutableList(length) {
                randomDigit(maxDigit)
            }.joinToString("")
        }
        return attempt
    }

    // Выбор следующей попытки на основании некоторой эвристики
    private fun heuristicChoice(): String {
        // Минимизируем похожесть двух последовательных попыток
        return possibleAnswers.shuffled().minBy { countDifference(it) } ?: ""
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

    // Нужно учесть все попытки (сделанные с помощью randomChoice())
    private fun checkAllConditions(attempt: String,
                                   cmp: (String, Int) -> Boolean = ::checkOutEquals): Boolean {
        var success = true
        for (i in allAttempts.indices) {
            if (!cmp(attempt, i)) {
                success = false
                break
            }
        }
        return success
    }

    // True, если prefix является возможным ответом
    private fun checkOutEquals(prefix: String, i: Int): Boolean {
        return checkAttempt(allAttempts[i], prefix) == allResponses[i]
    }

    // True, если prefix можно дополнить до ответа
    private fun checkOutUpperBound(prefix: String, i: Int): Boolean {
        val (aCount, bCount) = checkAttempt(allAttempts[i], prefix)
        val (aBound, bBound) = allResponses[i]
        // prefix должен содержать не больше "A" и "B", чем требуется
        return aCount <= aBound && bCount <= bBound
    }

    // True, если prefix можно дополнить до ответа
    private fun checkOutLowerBound(prefix: String, i: Int): Boolean {
        val (aCount, bCount) = checkAttempt(allAttempts[i], prefix, allAttempts[i].length)
        val (aBound, bBound) = allResponses[i]
        val aRequired = aBound - aCount
        val bRequired = bBound - bCount

        // Максимальное значение "A" и "B", которое можно получить, дополняя prefix
        val aRemainder = length - prefix.length
        val bRemainder = maxNumberOfB[i][aRemainder]

        // В prefix должно хватать места для необходимых "A" и "B"
        return aRequired <= aRemainder && bRequired <= bRemainder
    }

    private fun generatePossible(prefix: String = "") {
        if (prefix.length == length) {
            if (checkAllConditions(prefix)) {
                possibleAnswers.add(prefix)
            }
            return
        }
        // Отсечение заранее неподходящих вариантов
        if (!checkAllConditions(prefix, ::checkOutUpperBound) ||
            !checkAllConditions(prefix, ::checkOutLowerBound)) {
            return
        }
        // Рекурсивный перебор вариантов
        for (i in 1..maxDigit) {
            generatePossible(prefix.plus(i))
        }
    }

}