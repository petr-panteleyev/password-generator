// Copyright © 2025-2026 Petr Panteleyev
// SPDX-License-Identifier: BSD-2-Clause
package org.panteleyev.passwdgen

import java.util.ResourceBundle

object Util {
    const val COLON = ":"

    fun uiString(bundle: ResourceBundle, key: String): String {
        return bundle.getString(key)
    }

    fun uiString(bundle: ResourceBundle, key: String, suffix: String): String {
        return bundle.getString(key) + suffix
    }
}