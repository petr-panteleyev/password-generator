/*
 Copyright © 2021-2023 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
package org.panteleyev.passwdgen

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import java.util.ResourceBundle

class PasswordGeneratorApplication : Application() {
    companion object {
        val RB: ResourceBundle = ResourceBundle.getBundle("org.panteleyev.passwdgen.bundles.PasswordGenerator")
        private const val CSS_PATH = "/org/panteleyev/passwdgen/res/generator.css"
        private const val ICON_PATH = "org/panteleyev/passwdgen/res/password.png"
    }

    override fun start(stage: Stage) {
        with (stage) {
            title = RB.getString("title")
            isResizable = false
            scene = Scene(GeneratorController()).also {
                it.stylesheets.add(CSS_PATH)
            }
            icons.add(Image(ICON_PATH))
            show()
        }
    }
}
