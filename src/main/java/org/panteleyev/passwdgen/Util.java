/*
 Copyright © 2025 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
package org.panteleyev.passwdgen;

import java.util.ResourceBundle;

final class Util {
    public static final String COLON = ":";

    public static String uiString(ResourceBundle bundle, String key) {
        return bundle.getString(key);
    }

    public static String uiString(ResourceBundle bundle, String key, String suffix) {
        return bundle.getString(key) + suffix;
    }
}
