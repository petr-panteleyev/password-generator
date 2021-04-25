/*
 Copyright (c) Petr Panteleyev. All rights reserved.
 Licensed under the BSD license. See LICENSE file in the project root for full license information.
 */
package org.panteleyev.passwdgen;

enum PasswordLength {
    FOUR(4),
    SIX(6),
    EIGHT(8),
    SIXTEEN(16),
    TWENTY_FOUR(24),
    THIRTY_TWO(32),
    FORTY(40),
    FORTY_EIGHT(48),
    FIFTY_SIX(56),
    SIXTY_FOUR(64);

    private final int length;

    PasswordLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return Integer.toString(length);
    }
}
