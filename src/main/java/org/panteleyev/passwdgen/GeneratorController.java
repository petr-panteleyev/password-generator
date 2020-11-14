/*
 Copyright (c) Petr Panteleyev. All rights reserved.
 Licensed under the BSD license. See LICENSE file in the project root for full license information.
 */
package org.panteleyev.passwdgen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

class GeneratorController extends BorderPane {
    private final ResourceBundle rb = ResourceBundle.getBundle(PasswordGeneratorApplication.BUNDLE_PATH);

    private final Generator g = new Generator();

    private final CheckBox digitsCheckBox = new CheckBox(rb.getString("digits"));
    private final CheckBox upperCaseCheckBox = new CheckBox(rb.getString("upperCase"));
    private final CheckBox lowerCaseCheckBox = new CheckBox(rb.getString("lowerCase"));
    private final CheckBox symbolsCheckBox = new CheckBox(rb.getString("symbols"));
    private final CheckBox avoidAmbiguousLettesCheckBox = new CheckBox(rb.getString("avoid"));
    private final TextField passwdField = new TextField();
    private final ComboBox<Integer> lengthComboBox = new ComboBox<>();

    GeneratorController() {
        // Main menu

        var genItem = new MenuItem(rb.getString("menuGenerate"));
        genItem.setOnAction(a -> onGenerate());
        genItem.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.SHORTCUT_DOWN));

        var exitItem = new MenuItem(rb.getString("menuExit"));
        exitItem.setOnAction(a -> onExit());

        var copyItem = new MenuItem(rb.getString("menuCopy"));
        copyItem.setOnAction(a -> onCopy());
        copyItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));

        var menuBar = new MenuBar(
                new Menu(rb.getString("menuFile"), null,
                        genItem, new SeparatorMenuItem(), exitItem),
                new Menu(rb.getString("menuEdit"), null,
                        copyItem)
        );

        menuBar.setUseSystemMenuBar(true);

        setTop(menuBar);

        var p1 = new BorderPane();
        p1.setCenter(passwdField);

        var lenLabel = new Label(rb.getString("length"));
        var hBox = new HBox(
                upperCaseCheckBox,
                lowerCaseCheckBox,
                digitsCheckBox,
                symbolsCheckBox,
                lenLabel,
                lengthComboBox
        );
        hBox.setAlignment(Pos.CENTER_LEFT);

        var m5 = new Insets(0, 0, 0, 5);
        HBox.setMargin(digitsCheckBox, m5);
        HBox.setMargin(lowerCaseCheckBox, m5);
        HBox.setMargin(symbolsCheckBox, m5);
        HBox.setMargin(lenLabel, m5);
        HBox.setMargin(lengthComboBox, new Insets(0, 0, 0, 3));

        var unixButton = new Button("UNIX");
        unixButton.setOnAction(a -> onUnixButtonPressed());
        var pinButton = new Button("PIN");
        pinButton.setOnAction(a -> onPinButtonPressed());

        var flow = new FlowPane(
                unixButton, pinButton,
                avoidAmbiguousLettesCheckBox
        );

        FlowPane.setMargin(pinButton, new Insets(0, 0, 0, 10));
        FlowPane.setMargin(avoidAmbiguousLettesCheckBox, new Insets(0, 0, 0, 20));
        avoidAmbiguousLettesCheckBox.setSelected(true);

        var vBox = new VBox(
                new TitledPane(rb.getString("password"), p1),
                new TitledPane("Options", hBox),
                new TitledPane("Presets", flow)
        );

        setCenter(vBox);

        // Initial state
        lengthComboBox.getItems().addAll(4, 6, 8, 16, 24, 32);
        lengthComboBox.getSelectionModel().select(5);
        upperCaseCheckBox.setSelected(true);
        lowerCaseCheckBox.setSelected(true);
        digitsCheckBox.setSelected(true);

        // bindings
        g.useDigitsProperty().bind(digitsCheckBox.selectedProperty());
        g.useLowerCaseProperty().bind(lowerCaseCheckBox.selectedProperty());
        g.useSymbolsProperty().bind(symbolsCheckBox.selectedProperty());
        g.useUpperCaseProperty().bind(upperCaseCheckBox.selectedProperty());
        g.avoidAmbiguousLettersProperty().bind(avoidAmbiguousLettesCheckBox.selectedProperty());
        g.lengthProperty().bind(lengthComboBox.getSelectionModel().selectedItemProperty());
        passwdField.textProperty().bind(g.passwordProperty());

    }

    private void onUnixButtonPressed() {
        upperCaseCheckBox.setSelected(true);
        lowerCaseCheckBox.setSelected(true);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(false);
        lengthComboBox.getSelectionModel().select(2);
        onGenerate();
    }

    private void onPinButtonPressed() {
        upperCaseCheckBox.setSelected(false);
        lowerCaseCheckBox.setSelected(false);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(false);
        avoidAmbiguousLettesCheckBox.setSelected(false);
        lengthComboBox.getSelectionModel().select(0);
        onGenerate();
    }

    private void onGenerate() {
        g.generate();
    }

    private void onCopy() {
        var cb = Clipboard.getSystemClipboard();
        var ct = new ClipboardContent();
        ct.putString(passwdField.getText());
        cb.setContent(ct);
    }

    private void onExit() {
        System.exit(0);
    }
}
