/*
 Copyright (c) Petr Panteleyev. All rights reserved.
 Licensed under the BSD license. See LICENSE file in the project root for full license information.
 */
package org.panteleyev.passwdgen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class PasswordGeneratorApplication extends Application {
    static final ResourceBundle RB = ResourceBundle.getBundle("org.panteleyev.passwdgen.bundles.PasswordGenerator");

    private static final String CSS_PATH = "/org/panteleyev/passwdgen/res/generator.css";
    private static final String ICON_PATH = "org/panteleyev/passwdgen/res/password.png";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(RB.getString("title"));
        stage.setResizable(false);

        var scene = new Scene(new GeneratorController());
        scene.getStylesheets().add(CSS_PATH);
        stage.setScene(scene);
        stage.getIcons().add(new Image(ICON_PATH));
        stage.show();
    }
}
