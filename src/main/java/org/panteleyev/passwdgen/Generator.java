package org.panteleyev.passwdgen;

/*
 * Copyright (c) Petr Panteleyev. All rights reserved.
 * Licensed under the BSD license. See LICENSE file in the project root for full license information.
 */

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class Generator {
    private final IntegerProperty length = new SimpleIntegerProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final BooleanProperty avoidAmbiguousLetters = new SimpleBooleanProperty();

    static final List<Character> UPPER_CASE_CHARS = List.of(
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    );

    static final List<Character> LOWER_CASE_CHARS = List.of(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    );

    static final List<Character> DIGITS = List.of(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    );

    static final List<Character> SYMBOLS = List.of(
        '@', '#', '$', '%', '&', '*', '(', ')', '-', '+', '=', '^', '.', ','
    );

    static final List<Character> BAD_LETTERS = List.of(
        'I', 'l', 'O', '0'
    );

    private enum Bucket {
        B_UPPER_CASE(UPPER_CASE_CHARS),
        B_LOWER_CASE(LOWER_CASE_CHARS),
        B_DIGITS(DIGITS),
        B_SYMBOLS(SYMBOLS);

        private final List<Character> chars;
        private final BooleanProperty used = new SimpleBooleanProperty(false);

        Bucket(List<Character> chars) {
            this.chars = chars;
        }

        BooleanProperty usedProperty() {
            return used;
        }

        char getChar(int index) {
            return chars.get(index);
        }

        int getSize() {
            return chars.size();
        }

        boolean check(String pwd) {
            for (var ch : pwd.toCharArray()) {
                if (chars.contains(ch)) {
                    return true;
                }
            }
            return false;
        }
    }

    private final List<Bucket> buckets = List.of(
        Bucket.B_UPPER_CASE, Bucket.B_LOWER_CASE, Bucket.B_DIGITS, Bucket.B_SYMBOLS
    );

    private final Random random = new Random(System.currentTimeMillis());

    Generator() {
    }

    BooleanProperty useDigitsProperty() {
        return Bucket.B_DIGITS.usedProperty();
    }

    BooleanProperty useLowerCaseProperty() {
        return Bucket.B_LOWER_CASE.usedProperty();
    }

    BooleanProperty useSymbolsProperty() {
        return Bucket.B_SYMBOLS.usedProperty();
    }

    BooleanProperty useUpperCaseProperty() {
        return Bucket.B_UPPER_CASE.usedProperty();
    }

    IntegerProperty lengthProperty() {
        return length;
    }

    ReadOnlyStringProperty passwordProperty() {
        return password;
    }

    BooleanProperty avoidAmbiguousLettersProperty() {
        return avoidAmbiguousLetters;
    }

    /**
     * Generates new password.
     *
     * @throws IllegalArgumentException if password length &lt; 4
     */
    void generate() {
        int len = length.getValue();

        if (len < 4) {
            throw new IllegalArgumentException("Password length must be 4 or greater");
        }

        var usedBuckets = buckets.stream()
            .filter(x -> x.usedProperty().getValue())
            .collect(Collectors.toList());

        if (usedBuckets.isEmpty()) {
            throw new IllegalArgumentException("At least one character set must be selected");
        }

        password.setValue("");
        while (password.getValue().isEmpty()) {
            var res = new StringBuilder();

            for (int i = 0; i < len; ++i) {
                var bucket = usedBuckets.get(random.nextInt(usedBuckets.size()));

                char sym = ' ';
                var symOk = false;
                while (!symOk) {
                    sym = bucket.getChar(random.nextInt(bucket.getSize()));
                    symOk = !avoidAmbiguousLetters.getValue() || !BAD_LETTERS.contains(sym);
                }
                res.append(sym);
            }

            var pwd = res.toString();

            if (usedBuckets.stream().allMatch(x -> x.check(pwd))) {
                password.setValue(pwd);
            }
        }
    }
}
