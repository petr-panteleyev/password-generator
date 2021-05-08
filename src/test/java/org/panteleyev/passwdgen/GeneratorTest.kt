/*
 Copyright (c) Petr Panteleyev. All rights reserved.
 Licensed under the BSD license. See LICENSE file in the project root for full license information.
 */
package org.panteleyev.passwdgen

import org.testng.Assert.assertEquals
import org.testng.Assert.assertFalse
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class GeneratorTest {
    @DataProvider(name = "testPasswordContentData")
    fun testPasswordContentData(): Array<Array<Any>> {
        return arrayOf(
            arrayOf(true, false, false, false, false),
            arrayOf(false, true, false, false, false),
            arrayOf(false, false, true, false, false),
            arrayOf(false, false, false, true, false),
            arrayOf(false, false, false, true, true),
            arrayOf(true, true, true, true, true),
            arrayOf(true, true, true, true, false)
        )
    }

    @Test
    fun testPasswordLength() {
        PasswordLength.values().forEach {
            assertEquals(Generator.generate(length = it).length, it.length)
        }
    }

    private fun contains(array: List<Char>, c: Char): Boolean {
        return array.contains(c)
    }

    @Test(dataProvider = "testPasswordContentData")
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
        assertEquals(Generator.generate(
            upperCase = false,
            lowerCase = false,
            digits = false,
            symbols = false
        ), Generator.ERROR_MESSAGE)
    }
}