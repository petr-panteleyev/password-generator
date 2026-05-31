// Copyright © 2024-2026 Petr Panteleyev
// SPDX-License-Identifier: BSD-2-Clause
package org.panteleyev.passwdgen.bundles

import java.util.ListResourceBundle

class UiBundle : ListResourceBundle() {
    override fun getContents(): Array<Array<Any>> = arrayOf(
        arrayOf(Internationalization.I18N_COPY, "Copy"),
        arrayOf(Internationalization.I18N_DIGITS, "Digits"),
        arrayOf(Internationalization.I18N_EDIT, "Edit"),
        arrayOf(Internationalization.I18N_EXIT, "Exit"),
        arrayOf(Internationalization.I18N_GENERATE, "Generate"),
        arrayOf(Internationalization.I18N_FILE, "File"),
        arrayOf(Internationalization.I18N_LENGTH, "Length"),
        arrayOf(Internationalization.I18N_OPTIONS, "Options"),
        arrayOf(Internationalization.I18N_PASSWORD, "Password"),
        arrayOf(Internationalization.I18N_PRESETS, "Presets"),
        arrayOf(Internationalization.I18N_SYMBOLS, "Symbols"),
        arrayOf(Internationalization.I18N_TITLE, "Password Generator"),
        //
        arrayOf(Internationalization.I18N_AVOID_AMBIGUOUS, "Avoid ambiguous letters"),
        arrayOf(Internationalization.I18N_LONG_PASSWORD, "Long Password"),
        arrayOf(Internationalization.I18N_LOWER_CASE, "Lower Case"),
        arrayOf(Internationalization.I18N_MEDIUM_PASSWORD, "Medium Password"),
        arrayOf(Internationalization.I18N_UPPER_CASE, "Upper Case")
    )
}