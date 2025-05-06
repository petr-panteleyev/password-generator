/*
 Copyright © 2024-2025 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
package org.panteleyev.passwdgen;

import javax.swing.*;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_U;

final class Shortcuts {
    private Shortcuts() {
    }

    static final KeyStroke CTRL_C = KeyStroke.getKeyStroke(VK_C, CTRL_DOWN_MASK);
    static final KeyStroke CTRL_G = KeyStroke.getKeyStroke(VK_G, CTRL_DOWN_MASK);
    static final KeyStroke CTRL_L = KeyStroke.getKeyStroke(VK_L, CTRL_DOWN_MASK);
    static final KeyStroke CTRL_M = KeyStroke.getKeyStroke(VK_M, CTRL_DOWN_MASK);
    static final KeyStroke CTRL_U = KeyStroke.getKeyStroke(VK_U, CTRL_DOWN_MASK);
    static final KeyStroke CTRL_P = KeyStroke.getKeyStroke(VK_P, CTRL_DOWN_MASK);
}
