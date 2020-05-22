package com.mkn.thegreatcombinator.benchmarks

import kotlin.system.measureTimeMillis

fun main() {
    val n = 50000
    val deltaTime = measureTimeMillis {
        val room = SpeedTest(4, 6)
        val (average, max) = room.tests(n)
        println("tests = $n")
        println("Average: $average, Max: $max")
    }
    println("time: $deltaTime ms")
    println("average time: ${deltaTime / n} ms")
}