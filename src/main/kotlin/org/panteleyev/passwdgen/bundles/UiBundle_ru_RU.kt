// Copyright © 2024-2026 Petr Panteleyev
// SPDX-License-Identifier: BSD-2-Clause
package org.panteleyev.passwdgen.bundles

import java.util.ListResourceBundle

class UiBundle_ru_RU : ListResourceBundle() {
    override fun getContents(): Array<Array<Any>> = arrayOf(
        arrayOf(Internationalization.I18N_COPY, "Копировать"),
        arrayOf(Internationalization.I18N_DIGITS, "Цифры"),
        arrayOf(Internationalization.I18N_EDIT, "Правка"),
        arrayOf(Internationalization.I18N_EXIT, "Выход"),
        arrayOf(Internationalization.I18N_GENERATE, "Генерировать"),
        arrayOf(Internationalization.I18N_FILE, "Файл"),
        arrayOf(Internationalization.I18N_LENGTH, "Длина"),
        arrayOf(Internationalization.I18N_OPTIONS, "Настройки"),
        arrayOf(Internationalization.I18N_PASSWORD, "Пароль"),
        arrayOf(Internationalization.I18N_PRESETS, "Варианты"),
        arrayOf(Internationalization.I18N_SYMBOLS, "Символы"),
        arrayOf(Internationalization.I18N_TITLE, "Генератор паролей"),
        //
        arrayOf(Internationalization.I18N_AVOID_AMBIGUOUS, "Избегать неоднозначных символов"),
        arrayOf(Internationalization.I18N_LONG_PASSWORD, "Длинный пароль"),
        arrayOf(Internationalization.I18N_LOWER_CASE, "Нижний регистр"),
        arrayOf(Internationalization.I18N_MEDIUM_PASSWORD, "Средний пароль"),
        arrayOf(Internationalization.I18N_UPPER_CASE, "Верхний регистр")
    )
}