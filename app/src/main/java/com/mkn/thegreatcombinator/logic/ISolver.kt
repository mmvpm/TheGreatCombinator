package com.mkn.thegreatcombinator.logic

interface ISolver {
    fun makeAttempt(): String
    fun parseResponse(response: Pair<Int, Int>)
}