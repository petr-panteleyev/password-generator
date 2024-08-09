/*
 Copyright © 2024 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
package org.panteleyev.passwdgen;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

final class Shortcuts {
    private Shortcuts() {
    }

    static final KeyCodeCombination SHORTCUT_C = new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN);
    static final KeyCodeCombination SHORTCUT_G = new KeyCodeCombination(KeyCode.G, KeyCombination.SHORTCUT_DOWN);
    static final KeyCodeCombination SHORTCUT_L = new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN);
    static final KeyCodeCombination SHORTCUT_M = new KeyCodeCombination(KeyCode.M, KeyCombination.SHORTCUT_DOWN);
    static final KeyCodeCombination SHORTCUT_P = new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN);
    static final KeyCodeCombination SHORTCUT_U = new KeyCodeCombination(KeyCode.U, KeyCombination.SHORTCUT_DOWN);
}
