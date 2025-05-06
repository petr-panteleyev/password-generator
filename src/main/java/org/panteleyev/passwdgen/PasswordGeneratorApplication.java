/*
 Copyright © 2021-2025 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
package org.panteleyev.passwdgen;

import com.formdev.flatlaf.FlatLightLaf;
import org.panteleyev.passwdgen.bundles.UiBundle;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

public class PasswordGeneratorApplication {
    static final ResourceBundle UI = getBundle(UiBundle.class.getCanonicalName());

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new GeneratorController().setVisible(true);
    }
}
