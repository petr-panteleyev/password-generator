package org.panteleyev.passwdgen;

/*
 * Copyright (c) Petr Panteleyev. All rights reserved.
 * Licensed under the BSD license. See LICENSE file in the project root for full license information.
 */

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test class for Generator
 */
@Test
public class GeneratorTest {
    @DataProvider(name="testPasswordLengthData")
    public Object[][] testPasswordLengthData() {
        return new Object[][] {
            { 4 },
            { 6 },
            { 7 },
            { 14 },
            { 25 },
            { 32 },
            { 128 },
            { 256 },
        };
    }

    @DataProvider(name="testPasswordLengthDataBadLength")
    public Object[][] testPasswordLengthDataBadLength() {
        return new Object[][] {
            { -1 },
            { 0 },
            { 1 },
            { 2 },
            { 3 },
        };
    }

    @DataProvider(name="testPasswordContentData")
    public Object[][] testPasswordContentData() {
        return new Object[][] {
            { true, false, false, false, false },
            { false, true, false, false, false },
            { false, false, true, false, false },
            { false, false, false, true, false },
            { false, false, false, true, true },
            { true, true, true, true, true },
            { true, true, true, true, false },
        };
    }

    @Test(dataProvider="testPasswordLengthData")
    public void testPasswordLength(int length) {
        Generator g = new Generator();
        g.useDigitsProperty().set(true);
        g.useLowerCaseProperty().set(true);
        g.useUpperCaseProperty().set(true);
        g.useSymbolsProperty().set(true);

        g.lengthProperty().set(length);
        g.generate();
        String res = g.passwordProperty().get();
        Assert.assertEquals(res.length(), length);
    }

    @Test(dataProvider="testPasswordLengthDataBadLength", expectedExceptions={IllegalArgumentException.class})
    public void testPasswordBadLength(int length) throws Exception {
        Generator g = new Generator();
        g.useDigitsProperty().set(true);
        g.useLowerCaseProperty().set(true);
        g.useUpperCaseProperty().set(true);
        g.useSymbolsProperty().set(true);
        g.lengthProperty().set(length);
        g.generate();
    }

    private boolean contains(List<Character> array, char c) {
        return array.contains(c);
    }

    @Test(dataProvider="testPasswordContentData")
    public void testPasswordContent(boolean useDigits, boolean useUpperCase, boolean useLowerCase, boolean useSymbols, boolean avoid) {
        Generator g = new Generator();

        g.useDigitsProperty().set(useDigits);
        g.useLowerCaseProperty().set(useLowerCase);
        g.useUpperCaseProperty().set(useUpperCase);
        g.useSymbolsProperty().set(useSymbols);
        g.lengthProperty().set(32);
        g.avoidAmbiguousLettersProperty().set(avoid);
        g.generate();

        String res = g.passwordProperty().get();

        for (int i = 0; i < res.length(); i++) {
            char ch = res.charAt(i);

            Assert.assertFalse(!useDigits && contains(Generator.DIGITS, ch));
            Assert.assertFalse(!useUpperCase && contains(Generator.UPPER_CASE_CHARS, ch));
            Assert.assertFalse(!useLowerCase && contains(Generator.LOWER_CASE_CHARS, ch));
            Assert.assertFalse(!useSymbols && contains(Generator.SYMBOLS, ch));
            Assert.assertFalse(avoid && contains(Generator.BAD_LETTERS, ch));
        }
    }

    @Test(expectedExceptions={IllegalArgumentException.class})
    public void testNoCharacterSetSelected() throws Exception {
        Generator g = new Generator();
        g.useDigitsProperty().set(false);
        g.useLowerCaseProperty().set(false);
        g.useUpperCaseProperty().set(false);
        g.useSymbolsProperty().set(false);
        g.lengthProperty().set(4);
        g.generate();
    }
}
