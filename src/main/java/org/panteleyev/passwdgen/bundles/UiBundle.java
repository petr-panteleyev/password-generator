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

public class UiBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {I18N_COPY, "Copy"},
                {I18N_DIGITS, "Digits"},
                {I18N_EDIT, "File"},
                {I18N_EXIT, "Exit"},
                {I18N_GENERATE, "Generate"},
                {I18N_FILE, "File"},
                {I18N_LENGTH, "Length"},
                {I18N_OPTIONS, "Options"},
                {I18N_PASSWORD, "Password"},
                {I18N_PRESETS, "Presets"},
                {I18N_SYMBOLS, "Symbols"},
                {I18N_TITLE, "Password Generator"},
                //
                {I18N_AVOID_AMBIGUOUS, "Avoid ambiguous letters"},
                {I18N_LONG_PASSWORD, "Long Password"},
                {I18N_LOWER_CASE, "Lower Case"},
                {I18N_MEDIUM_PASSWORD, "Medium Password"},
                {I18N_UPPER_CASE, "Upper Case"}
        };
    }
}
