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

package org.panteleyev.passwdgen.test

import org.panteleyev.passwdgen.Generator
import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

/**
 * Test class for Generator
 */
@Test
class GeneratorTest {
    @DataProvider(name = "testPasswordLengthData")
    fun testPasswordLengthData(): Array<Array<Any>> {
        return arrayOf(
                arrayOf<Any>(4),
                arrayOf<Any>(6),
                arrayOf<Any>(7),
                arrayOf<Any>(14),
                arrayOf<Any>(25),
                arrayOf<Any>(32),
                arrayOf<Any>(128),
                arrayOf<Any>(256))
    }

    @DataProvider(name = "testPasswordLengthDataBadLength")
    fun testPasswordLengthDataBadLength(): Array<Array<Any>> {
        return arrayOf(
                arrayOf<Any>(-1),
                arrayOf<Any>(0),
                arrayOf<Any>(1),
                arrayOf<Any>(2),
                arrayOf<Any>(3))
    }

    @DataProvider(name = "testPasswordContentData")
    fun testPasswordContentData(): Array<Array<Any>> {
        return arrayOf(
                arrayOf<Any>(true, false, false, false, false),
                arrayOf<Any>(false, true, false, false, false),
                arrayOf<Any>(false, false, true, false, false),
                arrayOf<Any>(false, false, false, true, false),
                arrayOf<Any>(false, false, false, true, true),
                arrayOf<Any>(true, true, true, true, true),
                arrayOf<Any>(true, true, true, true, false))
    }

    @Test(dataProvider = "testPasswordLengthData")
    fun testPasswordLength(length: Int) {
        Generator.useDigitsProperty.set(true)
        Generator.useLowerCaseProperty.set(true)
        Generator.useUpperCaseProperty.set(true)
        Generator.useSymbolsProperty.set(true)

        Generator.lengthProperty.set(length)
        Generator.generate()
        val res = Generator.passwordProperty.get()
        Assert.assertEquals(res.length, length)
    }

    @Test(dataProvider = "testPasswordLengthDataBadLength", expectedExceptions = arrayOf(IllegalArgumentException::class))
    @Throws(Exception::class)
    fun testPasswordBadLength(length: Int) {
        Generator.useDigitsProperty.set(true)
        Generator.useLowerCaseProperty.set(true)
        Generator.useUpperCaseProperty.set(true)
        Generator.useSymbolsProperty.set(true)
        Generator.lengthProperty.set(length)
        Generator.generate()
    }

    private fun contains(array: Array<Char>, c: Char): Boolean {
        return array.contains(c)
    }

    @Test(dataProvider = "testPasswordContentData")
    fun testPasswordContent(useDigits: Boolean, useUpperCase: Boolean, useLowerCase: Boolean, useSymbols: Boolean, avoid: Boolean) {
        Generator.useDigitsProperty.set(useDigits)
        Generator.useLowerCaseProperty.set(useLowerCase)
        Generator.useUpperCaseProperty.set(useUpperCase)
        Generator.useSymbolsProperty.set(useSymbols)
        Generator.lengthProperty.set(32)
        Generator.avoidAmbiguousLettersProperty.set(avoid)
        Generator.generate()

        val res = Generator.passwordProperty.get()

        for (i in 0..res.length - 1) {
            val ch = res[i]

            Assert.assertFalse(!useDigits && contains(Generator.DIGITS, ch))
            Assert.assertFalse(!useUpperCase && contains(Generator.UPPER_CASE_CHARS, ch))
            Assert.assertFalse(!useLowerCase && contains(Generator.LOWER_CASE_CHARS, ch))
            Assert.assertFalse(!useSymbols && contains(Generator.SYMBOLS, ch))
            Assert.assertFalse(avoid && contains(Generator.BAD_LETTERS, ch))
        }
    }

    @Test(expectedExceptions = arrayOf(IllegalArgumentException::class))
    fun testNoCharacterSetSelected() {
        Generator.useDigitsProperty.set(false)
        Generator.useLowerCaseProperty.set(false)
        Generator.useUpperCaseProperty.set(false)
        Generator.useSymbolsProperty.set(false)
        Generator.lengthProperty.set(4)
        Generator.generate()
    }
}
