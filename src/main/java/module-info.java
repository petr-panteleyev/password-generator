/*
 Copyright © 2017-2025 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
open module password.generator {
    requires java.base;
    requires java.desktop;
    requires com.formdev.flatlaf;
    requires org.panteleyev.commons;

    exports org.panteleyev.passwdgen;
}