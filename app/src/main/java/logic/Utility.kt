import kotlin.math.abs
import kotlin.random.Random

class Utility {
    companion object {

        fun randomDigit(mod: Int): Int {
            return abs(Random.nextInt()) % mod + 1
        }

        fun check(attempt: String, answer: String, length: Int): Pair<Int, Int> {
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

        fun inputValidation(input: String?, length: Int, maxDigit: Int): Boolean {
            if (input == null || input.length != length) {
                return false
            }
            for (i in 0 until length) {
                if (input[i] !in '1'..('0' + maxDigit)) {
                    return false
                }
            }
            return true
        }

    }
}