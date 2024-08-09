/*
 Copyright © 2017-2024 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
open module password.generator {
    requires java.base;
    requires javafx.base;
    requires javafx.controls;

    requires org.panteleyev.commons;
    requires org.panteleyev.fx;

    exports org.panteleyev.passwdgen;
}