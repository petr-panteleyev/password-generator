/*
 Copyright (c) Petr Panteleyev. All rights reserved.
 Licensed under the BSD license. See LICENSE file in the project root for full license information.
 */
package org.panteleyev.passwdgen;

import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Test
public class GeneratorTest {
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

    @Test
    public void testPasswordLength() {
        var g = new Generator();
        g.useDigitsProperty().set(true);
        g.useLowerCaseProperty().set(true);
        g.useUpperCaseProperty().set(true);
        g.useSymbolsProperty().set(true);

        Arrays.stream(PasswordLength.values()).forEach(length -> {
            g.lengthProperty().set(length);
            g.generate();
            var res = g.passwordProperty().get();
            assertEquals(res.length(), length.getLength());
        });
    }

    private boolean contains(List<Character> array, char c) {
        return array.contains(c);
    }

    @Test(dataProvider="testPasswordContentData")
    public void testPasswordContent(boolean useDigits, boolean useUpperCase, boolean useLowerCase, boolean useSymbols, boolean avoid) {
        var g = new Generator();

        g.useDigitsProperty().set(useDigits);
        g.useLowerCaseProperty().set(useLowerCase);
        g.useUpperCaseProperty().set(useUpperCase);
        g.useSymbolsProperty().set(useSymbols);
        g.lengthProperty().set(PasswordLength.THIRTY_TWO);
        g.avoidAmbiguousLettersProperty().set(avoid);
        g.generate();

        String res = g.passwordProperty().get();

        for (int i = 0; i < res.length(); i++) {
            var ch = res.charAt(i);

            assertFalse(!useDigits && contains(Generator.DIGITS, ch));
            assertFalse(!useUpperCase && contains(Generator.UPPER_CASE_CHARS, ch));
            assertFalse(!useLowerCase && contains(Generator.LOWER_CASE_CHARS, ch));
            assertFalse(!useSymbols && contains(Generator.SYMBOLS, ch));
            assertFalse(avoid && contains(Generator.BAD_LETTERS, ch));
        }
    }

    @Test
    public void testNoCharacterSetSelected() throws Exception {
        var g = new Generator();
        g.useDigitsProperty().set(false);
        g.useLowerCaseProperty().set(false);
        g.useUpperCaseProperty().set(false);
        g.useSymbolsProperty().set(false);
        g.lengthProperty().set(PasswordLength.FOUR);
        g.generate();
        assertEquals(g.passwordProperty().get(), Generator.ERROR_MESSAGE);
    }
}
