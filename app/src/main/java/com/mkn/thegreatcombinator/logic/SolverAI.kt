package com.mkn.thegreatcombinator.logic

import kotlin.math.min
import kotlin.math.max
import kotlin.math.pow

class SolverAI(private val length: Int = 4,
               private val maxDigit: Int = 6) : ISolver {

    private inner class Data(val attempt: String,
                             var response: Pair<Int, Int> = Pair(0, 0)) {

        // maxNumberOfB[i] - максимально возможное количество "B" (в response),
        // которое можно получить, используя ровно i позиций в попытке
        var maxNumberOfB = MutableList(max(length, maxDigit) + 1) {0}.apply {
            // Количество каждой из цифр в attempt
            attempt.forEach { this[it - '0'] += 1 }
            this.sortDescending()
            for (i in 1 until this.size) {
                this[i] += this[i - 1]
            }
            this.add(0, 0)
        }.toIntArray()

        // True, если prefix является возможным ответом
        fun checkOutEquals(prefix: String): Boolean
                = checkAttempt(attempt, prefix) == response

        // True, если prefix можно дополнить до ответа
        fun checkOutUpperBound(prefix: String): Boolean {
            val (aCount, bCount) = checkAttempt(attempt, prefix)
            val (aBound, bBound) = response
            // prefix должен содержать не больше "A" и "B", чем требуется
            return aCount <= aBound && bCount <= bBound
        }

        // True, если prefix можно дополнить до ответа
        fun checkOutLowerBound(prefix: String): Boolean {
            val (aCount, bCount) = checkAttempt(attempt, prefix, attempt.length)
            val (aBound, bBound) = response
            val aRequired = aBound - aCount
            val bRequired = bBound - bCount

            // Максимальное значение "A" и "B", которое можно получить, дополняя prefix
            val aRemainder = length - prefix.length
            val bRemainder = maxNumberOfB[aRemainder]

            // В prefix должно хватать места для необходимых "A" и "B"
            return aRequired <= aRemainder && bRequired <= bRemainder
        }

    }

    // Последняя сделанная попытка и ответ на неё
    private var lastAttempt: String = ""
    private var lastResponse: Pair<Int, Int> = Pair(0, 0)
    private var alreadyGenerated = false

    // Все сделанные попытки
    private val allAttempts: MutableList<Data> = mutableListOf()
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
    private var randomRuns =
    if (sourceSize > timeBoundNext)
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
        allAttempts.add(Data(lastAttempt))
        return lastAttempt
    }

    override fun parseResponse(response: Pair<Int, Int>): Boolean {
        lastResponse = response
        allAttempts.last().response = response

        // Повезло, можно запускать основной алгоритм
        if (lastResponse.first >= length / 2) {
            randomRuns = 0
        }
        // Пока запускается randomChoice(), которому не требуется possibleAnswers
        if (allAttempts.size < randomRuns) {
            return true
        }
        // При первом запуске
        if (!alreadyGenerated) {
            generatePossible()
            alreadyGenerated = true
            return true
        }
        // Исключение неверных ответов
        possibleAnswers.removeIf {
            checkAttempt(lastAttempt, it) != lastResponse
        }
        // False, если пользователь ошибся в подсчёте
        if (possibleAnswers.isEmpty()) {
            restart()
            return false
        }
        return true
    }

    // Пользователь нажал Заново/Сдаться
    fun restart() {
        lastAttempt = ""
        lastResponse = Pair(0, 0)

        possibleAnswers.clear()
        allAttempts.clear()

        randomRuns =
        if (sourceSize > timeBoundNext)
            magicList[length - 5][maxDigit - 4]
        else
            0
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

        var count = 0
        var attempt: String = lastAttempt
        // Пытаемся выбрать то, что может быть ответом
        while (!allAttempts.all { it.checkOutEquals(attempt) } && count < timeBoundNext) {
            attempt = MutableList(length) {
                randomDigit(maxDigit)
            }.joinToString("")
            count += 1
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

    private fun generatePossible(prefix: String = "") {
        if (prefix.length == length) {
            // Нужно учесть все попытки (сделанные с помощью randomChoice())
            if (allAttempts.all { it.checkOutEquals(prefix) }) {
                possibleAnswers.add(prefix)
            }
            return
        }
        // Отсечение заранее неподходящих вариантов
        if (!allAttempts.all { it.checkOutLowerBound(prefix) && it.checkOutUpperBound(prefix) }) {
            return
        }
        // Рекурсивный перебор вариантов
        for (i in 1..maxDigit) {
            generatePossible(prefix.plus(i))
        }
    }

}