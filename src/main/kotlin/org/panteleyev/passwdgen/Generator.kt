/*
 * Copyright (c) 2017, Petr Panteleyev <petr@panteleyev.org>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.panteleyev.passwdgen

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import java.util.Random

internal object Generator {
    internal val UPPER_CASE_CHARS = arrayOf(
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    )

    internal val LOWER_CASE_CHARS = arrayOf(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    )

    internal val DIGITS = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    internal val SYMBOLS = arrayOf('@', '#', '$', '%', '&', '*', '(', ')', '-', '+', '=', '^', '.', ',')

    internal val BAD_LETTERS = arrayOf('I', 'l', 'O', '0')

    private enum class Bucket(private val chars: Array<Char>) {
        B_UPPER_CASE(UPPER_CASE_CHARS),
        B_LOWER_CASE(LOWER_CASE_CHARS),
        B_DIGITS(DIGITS),
        B_SYMBOLS(SYMBOLS);

        val used = SimpleBooleanProperty(false)

        val size: Int
            get() = chars.size

        operator fun get(index: Int): Char {
            return chars[index]
        }

        fun check(pwd: String): Boolean {
            for (ch in pwd.toCharArray()) {
                if (chars.contains(ch)) {
                    return true
                }
            }
            return false
        }
    }

    private val buckets = listOf(
            Bucket.B_UPPER_CASE,
            Bucket.B_LOWER_CASE,
            Bucket.B_DIGITS,
            Bucket.B_SYMBOLS
    )

    private val random = Random(System.currentTimeMillis())

    val lengthProperty = SimpleIntegerProperty()
    val passwordProperty = SimpleStringProperty()
    val avoidAmbiguousLettersProperty = SimpleBooleanProperty()

    val useDigitsProperty
        get() = Bucket.B_DIGITS.used

    val useLowerCaseProperty
        get() = Bucket.B_LOWER_CASE.used

    val useSymbolsProperty
        get() = Bucket.B_SYMBOLS.used

    val useUpperCaseProperty
        get() = Bucket.B_UPPER_CASE.used

    /**
     * Generates new password.
     * @throws IllegalArgumentException if password length &lt; 4
     */
    fun generate() {
        val len = lengthProperty.value

        if (len < 4) {
            throw IllegalArgumentException("Password length must be 4 or greater")
        }

        val usedBuckets = buckets.filter { it.used.value }

        if (usedBuckets.isEmpty()) {
            throw IllegalArgumentException("At least one character set must be selected")
        }

        passwordProperty.value = ""
        while (passwordProperty.value.isEmpty()) {
            val res = StringBuilder()

            for (i in 0..len - 1) {
                val bucket = usedBuckets[random.nextInt(usedBuckets.size)]

                var sym = ' '
                var symOk = false
                while (!symOk) {
                    sym = bucket[random.nextInt(bucket.size)]
                    symOk = !avoidAmbiguousLettersProperty.value || !BAD_LETTERS.contains(sym)
                }
                res.append(sym)
            }

            val pwd = res.toString()

            if (usedBuckets.all { it.check(pwd) }) {
                passwordProperty.value = pwd
            }
        }
    }
}
