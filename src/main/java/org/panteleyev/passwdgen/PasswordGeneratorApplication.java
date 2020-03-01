package org.panteleyev.passwdgen;

/*
 * Copyright (c) Petr Panteleyev. All rights reserved.
 * Licensed under the BSD license. See LICENSE file in the project root for full license information.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class PasswordGeneratorApplication extends Application {
    static final String BUNDLE_PATH = "org.panteleyev.passwdgen.bundles.PasswordGenerator";
    private static final String CSS_PATH = "/org/panteleyev/passwdgen/res/generator.css";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var bundle = ResourceBundle.getBundle(BUNDLE_PATH);

        stage.setTitle(bundle.getString("title"));
        stage.setResizable(false);

        var scene = new Scene(new GeneratorController());
        scene.getStylesheets().add(CSS_PATH);
        stage.setScene(scene);
        stage.show();
    }
}
