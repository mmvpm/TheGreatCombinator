package com.mkn.thegreatcombinator

import org.junit.Test
import org.junit.Assert.*
import com.mkn.thegreatcombinator.logic.*


class UtilityTest {

    @Test
    fun randomDigit_testValidation() {
        for (len in 1..10) {
            for (i in 1..len) {
                val exp = "in 1..$len"
                val act = randomDigit(len)
                assertTrue("[i] Expected: $exp, Actual: $act", act in 1..len)
            }
        }
    }


    class OneTest(val att: String, val ans: String, val a: Int, val b: Int) {
        private val cnt = checkAttempt(att, ans)

        fun exp(): String = "($a, $b)"
        fun act(): String = "(${cnt.first}, ${cnt.second})"
        fun run(): Boolean = checkAttempt(att, ans) == Pair(a, b)
    }

    @Test
    fun checkAttempt_length4_maxDigit6() {
        val tests = listOf (
            OneTest("1546", "4535", 1, 1),
            OneTest("3214", "6511", 1, 0),
            OneTest("5536", "5436", 3, 1),
            OneTest("6443", "4646", 1, 2),
            OneTest("1362", "5564", 1, 0),
            OneTest("1551", "1551", 4, 0),
            OneTest("2566", "2622", 1, 2),
            OneTest("5645", "6343", 1, 1),
            OneTest("3423", "1415", 1, 0),
            OneTest("6433", "4412", 1, 0),
            OneTest("4451", "2456", 2, 1),
            OneTest("5222", "2545", 0, 4),
            OneTest("3656", "2122", 0, 0),
            OneTest("5531", "6331", 2, 0),
            OneTest("6156", "4554", 1, 0),
            OneTest("6624", "6445", 1, 2),
            OneTest("4611", "6252", 0, 1),
            OneTest("6545", "6412", 1, 1),
            OneTest("6621", "1125", 1, 1),
            OneTest("6246", "3243", 2, 0),
            OneTest("1243", "5552", 0, 1),
            OneTest("3561", "3531", 3, 0),
            OneTest("3633", "3213", 2, 1),
            OneTest("3662", "1652", 2, 1),
            OneTest("3321", "5563", 0, 2)
        )

        tests.map {
            assertTrue("[${it.att}, ${it.ans}] Expected: ${it.exp()}, Actual: ${it.act()}", it.run())
        }
    }

    @Test
    fun checkAttempt_length5_maxDigit8() {
        val tests = listOf (
            OneTest("58411", "36321", 1, 1),
            OneTest("33733", "51351", 0, 4),
            OneTest("28383", "12584", 1, 2),
            OneTest("36774", "26881", 1, 0),
            OneTest("42421", "61414", 1, 2),
            OneTest("62452", "31263", 0, 3),
            OneTest("85141", "76636", 0, 0),
            OneTest("15345", "41254", 0, 4),
            OneTest("87711", "13785", 1, 4),
            OneTest("42428", "63867", 0, 1),
            OneTest("25717", "26252", 1, 1),
            OneTest("43455", "26347", 0, 3),
            OneTest("46553", "87634", 0, 3),
            OneTest("37143", "25136", 1, 2),
            OneTest("12518", "55374", 0, 1),
            OneTest("31555", "27337", 0, 1),
            OneTest("62184", "31747", 0, 2),
            OneTest("74773", "23154", 0, 2),
            OneTest("58686", "58131", 2, 1),
            OneTest("78618", "34668", 2, 1),
            OneTest("86524", "86525", 4, 0),
            OneTest("32474", "32476", 4, 1),
            OneTest("36687", "36684", 4, 0),
            OneTest("38667", "38668", 4, 0),
            OneTest("67273", "67273", 5, 0)
        )

        tests.map {
            assertTrue("[${it.att}, ${it.ans}] Expected: ${it.exp()}, Actual: ${it.act()}", it.run())
        }
    }

    @Test
    fun checkAttempt_length6_maxDigit9() {
        val tests = listOf (
            OneTest("368432", "129986", 0, 3),
            OneTest("652954", "155279", 1, 3),
            OneTest("457731", "399212", 0, 2),
            OneTest("121324", "272516", 0, 4),
            OneTest("913734", "492334", 2, 2),
            OneTest("565874", "658575", 1, 4),
            OneTest("591365", "227846", 0, 1),
            OneTest("252936", "215538", 2, 2),
            OneTest("589678", "914149", 0, 1),
            OneTest("159552", "612798", 0, 3),
            OneTest("622117", "227871", 1, 4),
            OneTest("571728", "115473", 0, 4),
            OneTest("559222", "568755", 1, 1),
            OneTest("933617", "265866", 0, 1),
            OneTest("391923", "771817", 1, 0),
            OneTest("195877", "518742", 0, 5),
            OneTest("382877", "258586", 0, 3),
            OneTest("246547", "734742", 1, 3),
            OneTest("144543", "448736", 1, 3),
            OneTest("894987", "662379", 0, 3),
            OneTest("627284", "627248", 4, 2),
            OneTest("263896", "263894", 5, 1),
            OneTest("111845", "111844", 5, 0),
            OneTest("294866", "294861", 5, 1),
            OneTest("673289", "673289", 6, 0)
        )

        tests.map {
            assertTrue("[${it.att}, ${it.ans}] Expected: ${it.exp()}, Actual: ${it.act()}", it.run())
        }
    }

    @Test
    fun checkAttempt_length8_maxDigit4() {
        val tests = listOf (
            OneTest("23114344", "11214413", 2, 6),
            OneTest("31432112", "14441441", 1, 3),
            OneTest("13442322", "44124244", 0, 6),
            OneTest("43114341", "42323333", 2, 3),
            OneTest("32243114", "32413313", 4, 4),
            OneTest("22442342", "44323322", 2, 6),
            OneTest("42412342", "21344123", 0, 8),
            OneTest("23444213", "32211323", 1, 4),
            OneTest("11343113", "12414122", 2, 3),
            OneTest("13221323", "42433414", 0, 8),
            OneTest("32424343", "32113222", 2, 3),
            OneTest("24332223", "33213423", 2, 6),
            OneTest("21424112", "43422232", 3, 2),
            OneTest("41112224", "23414341", 1, 7),
            OneTest("44443233", "24241442", 2, 3),
            OneTest("24441311", "12121243", 1, 7),
            OneTest("24244421", "12444314", 2, 6),
            OneTest("13344312", "32232222", 1, 3),
            OneTest("14343431", "32132414", 1, 7),
            OneTest("31433333", "44323111", 1, 7),
            OneTest("22223323", "22223313", 7, 1),
            OneTest("23441324", "23441343", 6, 2),
            OneTest("12433214", "12433234", 7, 1),
            OneTest("23231324", "23231323", 7, 0),
            OneTest("23333323", "23333323", 8, 0)
        )

        tests.map {
            assertTrue("[${it.att}, ${it.ans}] Expected: ${it.exp()}, Actual: ${it.act()}", it.run())
        }
    }


    @Test
    fun incZeroBased_testValidation() {
        val mod = 17
        for (i in 0 until mod) {
            val exp = (if (i < mod - 1) i + 1 else 0).toString()
            val act = incZeroBased(i.toString(), mod)
            assertTrue("[i] Expected: $exp, Actual: $act", exp == act)
        }
    }

    @Test
    fun incOneBased_testValidation() {
        val mod = 17
        for (i in 1..mod) {
            val exp = (if (i < mod) i + 1 else 1).toString()
            val act = incOneBased(i.toString(), mod)
            assertTrue("[i] Expected: $exp, Actual: $act", exp == act)
        }
    }

    @Test
    fun decZeroBased_testValidation() {
        val mod = 17
        for (i in 0 until mod) {
            val exp = (if (i > 0) i - 1 else mod - 1).toString()
            val act = decZeroBased(i.toString(), mod)
            assertTrue("[i] Expected: $exp, Actual: $act", exp == act)
        }
    }

    @Test
    fun decOneBased_testValidation() {
        val mod = 17
        for (i in 1..mod) {
            val exp = (if (i > 1) i - 1 else mod).toString()
            val act = decOneBased(i.toString(), mod)
            assertTrue("[i] Expected: $exp, Actual: $act", exp == act)
        }
    }

}