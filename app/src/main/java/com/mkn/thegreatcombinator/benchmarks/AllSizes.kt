package com.mkn.thegreatcombinator.benchmarks

import kotlin.system.measureTimeMillis

fun main() {
    val cap = 10
    val maxResults = Array(cap) { IntArray(cap) }
    val averageResults = Array(cap) { DoubleArray(cap) }
    //                maxDigit =  4    5    6    7    8    9    // length
    val division = listOf(listOf(1e5, 8e4, 4e4, 4e4, 2e4, 2e4), // 3
                          listOf(5e4, 2e4, 2e4, 1e4, 8e3, 5e3), // 4
                          listOf(1e4, 8e3, 5e3, 2e3, 1e3, 8e2), // 5
                          listOf(4e3, 2e3, 8e2, 5e2, 4e2, 2e2), // 6
                          listOf(1e3, 8e2, 4e2, 2e2, 1e2, 8e1), // 7
                          listOf(5e2, 4e2, 1e2, 5e1, 2e1, 2e1)) // 8

    for (i in 0..5) {
        for (j in 0..5) {
            val length = i + 3
            val maxDigit = j + 4
            val n = division[i][j].toInt()

            val deltaTime = measureTimeMillis {
                val room = SpeedTest(length, maxDigit)
                val (average, max) = room.tests(n)

                maxResults[i][j] = max
                averageResults[i][j] = average

                println("tests = $n")
                println("($length, $maxDigit) -> Average: $average, Max: $max")
            }
            println("time: $deltaTime ms")
            println("average time: ${deltaTime / n} ms\n")
        }
    }

    for (i in 0..5) {
        println(maxResults[i].copyOfRange(0, 6).joinToString(" ") { String.format("%6d", it) })
    }
    println()
    for (i in 0..5) {
        println(averageResults[i].copyOfRange(0, 6).joinToString(" ") { String.format("%.4f", it) })
    }
}