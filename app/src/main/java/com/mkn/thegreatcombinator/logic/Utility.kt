package com.mkn.thegreatcombinator.logic

import kotlin.math.abs
import kotlin.random.Random

fun randomDigit(mod: Int): Int = abs(Random.nextInt()) % mod + 1

fun checkAttempt(attempt: String, answer: String, length: Int): Pair<Int, Int> {
    var aCount = 0
    var bCount = 0
    for (i in 0 until length) {
        if (attempt[i] == answer[i]) {
            aCount += 1
        }
        else if (attempt[i] in answer) {
            bCount += 1
        }
    }
    return Pair(aCount, bCount)
}

fun incZeroBased(value: String, mod: Int): String
    = ((value.toInt() + 1) % mod).toString()

fun decZeroBased(value: String, mod: Int): String
    = ((value.toInt() - 1 + mod) % mod).toString()

fun incOneBased(value: String, mod: Int): String
    = (value.toInt() % mod + 1).toString()

fun decOneBased(value: String, mod: Int): String
    = ((value.toInt() - 2 + mod) % mod + 1).toString()