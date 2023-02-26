/*
 Copyright © 2021-2023 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
package org.panteleyev.passwdgen

import kotlin.random.Random

object Generator {
    const val ERROR_MESSAGE = "Error: at least one character set must be selected"

    val UPPER_CASE_CHARS = listOf(
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    )

    val LOWER_CASE_CHARS = listOf(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    )

    val DIGITS = listOf(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    )

    val SYMBOLS = listOf(
        '@', '#', '$', '%', '&', '*', '(', ')', '-', '+', '=', '^', '.', ','
    )

    val BAD_LETTERS = listOf(
        'I', 'l', 'O'
    )

    private enum class Bucket(private val chars: List<Char>) {
        B_UPPER_CASE(UPPER_CASE_CHARS),
        B_LOWER_CASE(LOWER_CASE_CHARS),
        B_DIGITS(DIGITS),
        B_SYMBOLS(SYMBOLS);

        var used: Boolean = false
        val size get() = chars.size

        operator fun get(index: Int) = chars[index]

        fun check(pwd: String): Boolean {
            return pwd.toCharArray().any {
                chars.contains(it)
            }
        }
    }

    private val buckets = listOf(
        Bucket.B_UPPER_CASE, Bucket.B_LOWER_CASE, Bucket.B_DIGITS, Bucket.B_SYMBOLS
    )

    private val random = Random(System.currentTimeMillis())

    fun generate(
        upperCase: Boolean = true,
        lowerCase: Boolean = true,
        digits: Boolean = true,
        symbols: Boolean = true,
        noAmbiguousLetters: Boolean = true,
        length: PasswordLength = PasswordLength.THIRTY_TWO
    ): String {
        Bucket.B_UPPER_CASE.used = upperCase
        Bucket.B_LOWER_CASE.used = lowerCase
        Bucket.B_DIGITS.used = digits
        Bucket.B_SYMBOLS.used = symbols

        val usedBuckets = buckets.filter { it.used }
        if (usedBuckets.isEmpty()) {
            return ERROR_MESSAGE
        }

        while (true) {
            val res = StringBuilder()
            for (i in 1..length.length) {
                val bucket = usedBuckets[random.nextInt(usedBuckets.size)]
                var sym = ' '
                var symOk = false

                while (!symOk) {
                    sym = bucket[random.nextInt(bucket.size)]
                    symOk = !noAmbiguousLetters || !BAD_LETTERS.contains(sym)
                }
                res.append(sym)
            }

            val pwd = res.toString()
            if (usedBuckets.all { it.check(pwd) }) {
                return pwd
            }
        }
    }
}
