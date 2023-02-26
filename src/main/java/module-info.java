/*
 Copyright © 2017-2023 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
open module password.generator {
    requires java.base;
    requires javafx.base;
    requires javafx.controls;

    requires kotlin.stdlib.jdk8;

    exports org.panteleyev.passwdgen;
}