// Copyright © 2017-2026 Petr Panteleyev
// SPDX-License-Identifier: BSD-2-Clause
open module password.generator {
    requires java.base;
    requires java.desktop;
    requires com.formdev.flatlaf;
    requires org.panteleyev.commons;
    requires kotlin.stdlib;

    exports org.panteleyev.passwdgen;
}