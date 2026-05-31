// Copyright © 2024-2026 Petr Panteleyev
// SPDX-License-Identifier: BSD-2-Clause
package org.panteleyev.passwdgen.bundles

import java.util.ResourceBundle

object Internationalization {
    val UI: ResourceBundle = ResourceBundle.getBundle(UiBundle::class.java.canonicalName)

    const val I18N_COPY = "I18N_COPY"
    const val I18N_DIGITS = "I18N_DIGITS"
    const val I18N_EDIT = "I18N_EDIT"
    const val I18N_EXIT = "I18N_EXIT"
    const val I18N_GENERATE = "I18N_GENERATE"
    const val I18N_FILE = "I18N_FILE"
    const val I18N_LENGTH = "I18N_LENGTH"
    const val I18N_OPTIONS = "I18N_OPTIONS"
    const val I18N_PASSWORD = "I18N_PASSWORD"
    const val I18N_PRESETS = "I18N_PRESETS"
    const val I18N_SYMBOLS = "I18N_SYMBOLS"
    const val I18N_TITLE = "I18N_TITLE"

    const val I18N_AVOID_AMBIGUOUS = "I18N_AVOID_AMBIGUOUS"
    const val I18N_LONG_PASSWORD = "I18N_LONG_PASSWORD"
    const val I18N_LOWER_CASE = "I18N_LOWER_CASE"
    const val I18N_MEDIUM_PASSWORD = "I18N_MEDIUM_PASSWORD"
    const val I18N_UPPER_CASE = "I18N_UPPER_CASE"
}