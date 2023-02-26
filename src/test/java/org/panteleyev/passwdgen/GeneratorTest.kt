/*
 Copyright © 2021-2023 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
package org.panteleyev.passwdgen

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class GeneratorTest {
    companion object {
        @JvmStatic
        fun testPasswordContentData(): List<Arguments> {
            return listOf(
                Arguments.of(true, false, false, false, false),
                Arguments.of(false, true, false, false, false),
                Arguments.of(false, false, true, false, false),
                Arguments.of(false, false, false, true, false),
                Arguments.of(false, false, false, true, true),
                Arguments.of(true, true, true, true, true),
                Arguments.of(true, true, true, true, false)
            )
        }
    }

    @Test
    fun testPasswordLength() {
        PasswordLength.values().forEach {
            assertEquals(it.length, Generator.generate(length = it).length)
        }
    }

    private fun contains(array: List<Char>, c: Char): Boolean {
        return array.contains(c)
    }

    @ParameterizedTest
    @MethodSource("testPasswordContentData")
    fun testPasswordContent(
        useDigits: Boolean,
        useUpperCase: Boolean,
        useLowerCase: Boolean,
        useSymbols: Boolean,
        avoid: Boolean
    ) {
        for (ch in Generator.generate(
            upperCase = useUpperCase,
            lowerCase = useLowerCase,
            digits = useDigits,
            symbols = useSymbols,
            noAmbiguousLetters = avoid
        )) {
            assertFalse(!useDigits && contains(Generator.DIGITS, ch))
            assertFalse(!useUpperCase && contains(Generator.UPPER_CASE_CHARS, ch))
            assertFalse(!useLowerCase && contains(Generator.LOWER_CASE_CHARS, ch))
            assertFalse(!useSymbols && contains(Generator.SYMBOLS, ch))
            assertFalse(avoid && contains(Generator.BAD_LETTERS, ch))
        }
    }

    @Test
    fun testNoCharacterSetSelected() {
        assertEquals(Generator.ERROR_MESSAGE, Generator.generate(
            upperCase = false,
            lowerCase = false,
            digits = false,
            symbols = false
        ))
    }
}