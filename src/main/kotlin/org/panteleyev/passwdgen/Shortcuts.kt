// Copyright © 2024-2026 Petr Panteleyev
// SPDX-License-Identifier: BSD-2-Clause
package org.panteleyev.passwdgen

import java.awt.event.InputEvent.CTRL_DOWN_MASK
import java.awt.event.KeyEvent.VK_C
import java.awt.event.KeyEvent.VK_G
import java.awt.event.KeyEvent.VK_L
import java.awt.event.KeyEvent.VK_M
import java.awt.event.KeyEvent.VK_P
import java.awt.event.KeyEvent.VK_U
import javax.swing.KeyStroke

object Shortcuts {
    val CTRL_C: KeyStroke = KeyStroke.getKeyStroke(VK_C, CTRL_DOWN_MASK)
    val CTRL_G: KeyStroke = KeyStroke.getKeyStroke(VK_G, CTRL_DOWN_MASK)
    val CTRL_L: KeyStroke = KeyStroke.getKeyStroke(VK_L, CTRL_DOWN_MASK)
    val CTRL_M: KeyStroke = KeyStroke.getKeyStroke(VK_M, CTRL_DOWN_MASK)
    val CTRL_U: KeyStroke = KeyStroke.getKeyStroke(VK_U, CTRL_DOWN_MASK)
    val CTRL_P: KeyStroke = KeyStroke.getKeyStroke(VK_P, CTRL_DOWN_MASK)
}