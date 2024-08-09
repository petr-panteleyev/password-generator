/*
 Copyright © 2024 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
package org.panteleyev.passwdgen.bundles;

import java.util.ListResourceBundle;

import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_AVOID_AMBIGUOUS;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_COPY;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_DIGITS;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_EDIT;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_EXIT;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_FILE;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_GENERATE;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_LENGTH;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_LONG_PASSWORD;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_LOWER_CASE;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_MEDIUM_PASSWORD;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_OPTIONS;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_PASSWORD;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_PRESETS;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_SYMBOLS;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_TITLE;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_UPPER_CASE;

public class UiBundle_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {I18N_COPY, "Копировать"},
                {I18N_DIGITS, "Цифры"},
                {I18N_EDIT, "Правка"},
                {I18N_EXIT, "Выход"},
                {I18N_GENERATE, "Генерировать"},
                {I18N_FILE, "Файл"},
                {I18N_LENGTH, "Длина"},
                {I18N_OPTIONS, "Настройки"},
                {I18N_PASSWORD, "Пароль"},
                {I18N_PRESETS, "Варианты"},
                {I18N_SYMBOLS, "Символы"},
                {I18N_TITLE, "Генератор паролей"},
                //
                {I18N_AVOID_AMBIGUOUS, "Избегать неоднозначных символов"},
                {I18N_LONG_PASSWORD, "Длинный пароль"},
                {I18N_LOWER_CASE, "Нижний регистр"},
                {I18N_MEDIUM_PASSWORD, "Средний пароль"},
                {I18N_UPPER_CASE, "Верхний регистр"}
        };
    }
}
