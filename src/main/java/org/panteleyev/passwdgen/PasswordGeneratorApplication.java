/*
 Copyright © 2021-2024 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
package org.panteleyev.passwdgen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.panteleyev.passwdgen.bundles.UiBundle;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;
import static org.panteleyev.fx.FxUtils.fxString;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_TITLE;

public class PasswordGeneratorApplication extends Application {
    static final ResourceBundle UI = getBundle(UiBundle.class.getCanonicalName());

    private static final String CSS_PATH = "/org/panteleyev/passwdgen/res/generator.css";
    private static final String ICON_PATH = "org/panteleyev/passwdgen/res/password.png";

    @Override
    public void start(Stage stage) {
        stage.setTitle(fxString(UI, I18N_TITLE));
        stage.setResizable(false);

        var scene = new Scene(new GeneratorController());
        scene.getStylesheets().add(CSS_PATH);
        stage.setScene(scene);

        stage.getIcons().add(new Image(ICON_PATH));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
