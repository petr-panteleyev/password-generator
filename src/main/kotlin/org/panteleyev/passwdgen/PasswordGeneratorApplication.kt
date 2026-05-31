// Copyright © 2021-2026 Petr Panteleyev
// SPDX-License-Identifier: BSD-2-Clause
package org.panteleyev.passwdgen

import com.formdev.flatlaf.FlatLightLaf
import javax.swing.SwingUtilities

fun main() {
    FlatLightLaf.setup()
    SwingUtilities.invokeLater { GeneratorController().isVisible = true }
}