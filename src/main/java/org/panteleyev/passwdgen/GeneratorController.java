/*
 Copyright (c) Petr Panteleyev. All rights reserved.
 Licensed under the BSD license. See LICENSE file in the project root for full license information.
 */
package org.panteleyev.passwdgen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static org.panteleyev.passwdgen.PasswordGeneratorApplication.RB;

class GeneratorController extends BorderPane {
    private final Generator g = new Generator();

    private final CheckBox digitsCheckBox = new CheckBox(RB.getString("digits"));
    private final CheckBox upperCaseCheckBox = new CheckBox(RB.getString("upperCase"));
    private final CheckBox lowerCaseCheckBox = new CheckBox(RB.getString("lowerCase"));
    private final CheckBox symbolsCheckBox = new CheckBox(RB.getString("symbols"));
    private final CheckBox avoidAmbiguousLettesCheckBox = new CheckBox(RB.getString("avoid"));
    private final TextField passwdField = new TextField();
    private final ComboBox<PasswordLength> lengthComboBox = new ComboBox<>();

    GeneratorController() {
        setTop(createMenuBar());

        var lenLabel = new Label(RB.getString("length"));
        var hBox = new HBox(
            upperCaseCheckBox,
            lowerCaseCheckBox,
            digitsCheckBox,
            symbolsCheckBox
        );
        hBox.setAlignment(Pos.CENTER_LEFT);
        var m5 = new Insets(0, 0, 0, 5);
        HBox.setMargin(digitsCheckBox, m5);
        HBox.setMargin(lowerCaseCheckBox, m5);
        HBox.setMargin(symbolsCheckBox, m5);

        var lenHBox = new HBox(
            lenLabel,
            lengthComboBox
        );
        lenHBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(lengthComboBox, new Insets(0, 0, 0, 3));


        var topInsets = new Insets(10, 0, 0, 0);
        VBox.setMargin(lenHBox, topInsets);
        VBox.setMargin(avoidAmbiguousLettesCheckBox, topInsets);
        avoidAmbiguousLettesCheckBox.setSelected(true);

        var vBox = new VBox(
            new TitledPane(RB.getString("password"),
                new BorderPane(passwdField)
            ),
            new TitledPane(RB.getString("options"),
                new VBox(
                    hBox,
                    lenHBox,
                    avoidAmbiguousLettesCheckBox
                )
            )
        );

        setCenter(vBox);

        // Initial state
        lengthComboBox.getItems().addAll(PasswordLength.values());
        lengthComboBox.getSelectionModel().select(PasswordLength.THIRTY_TWO);
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

    private MenuBar createMenuBar() {
        var genItem = new MenuItem(RB.getString("menuGenerate"));
        genItem.setOnAction(a -> onGenerate());
        genItem.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.SHORTCUT_DOWN));
        var unixItem = new MenuItem("Unix");
        unixItem.setOnAction(a -> onUnix());
        unixItem.setAccelerator(new KeyCodeCombination(KeyCode.U, KeyCombination.SHORTCUT_DOWN));
        var pinItem = new MenuItem("PIN");
        pinItem.setOnAction(a -> onPin());
        pinItem.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN));
        var mediumPasswordItem = new MenuItem(RB.getString("medium_password"));
        mediumPasswordItem.setOnAction(a -> onMediumPassword());
        mediumPasswordItem.setAccelerator(new KeyCodeCombination(KeyCode.M, KeyCombination.SHORTCUT_DOWN));
        var longPasswordItem = new MenuItem(RB.getString("long_password"));
        longPasswordItem.setOnAction(a -> onLongPassword());
        longPasswordItem.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN));

        var exitItem = new MenuItem(RB.getString("menuExit"));
        exitItem.setOnAction(a -> onExit());

        var copyItem = new MenuItem(RB.getString("menuCopy"));
        copyItem.setOnAction(a -> onCopy());
        copyItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));

        var menuBar = new MenuBar(
            new Menu(RB.getString("menuFile"), null,
                genItem,
                new SeparatorMenuItem(),
                exitItem),
            new Menu(RB.getString("menuEdit"), null,
                copyItem),
            new Menu(RB.getString("presets"), null,
                mediumPasswordItem,
                longPasswordItem,
                new SeparatorMenuItem(),
                unixItem,
                pinItem)
        );

        menuBar.setUseSystemMenuBar(true);
        return menuBar;
    }

    private void onUnix() {
        upperCaseCheckBox.setSelected(true);
        lowerCaseCheckBox.setSelected(true);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(true);
        lengthComboBox.getSelectionModel().select(PasswordLength.EIGHT);
        onGenerate();
    }

    private void onPin() {
        upperCaseCheckBox.setSelected(false);
        lowerCaseCheckBox.setSelected(false);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(false);
        lengthComboBox.getSelectionModel().select(PasswordLength.FOUR);
        onGenerate();
    }

    private void onMediumPassword() {
        upperCaseCheckBox.setSelected(true);
        lowerCaseCheckBox.setSelected(true);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(true);
        lengthComboBox.getSelectionModel().select(PasswordLength.SIXTEEN);
        onGenerate();
    }

    private void onLongPassword() {
        upperCaseCheckBox.setSelected(true);
        lowerCaseCheckBox.setSelected(true);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(true);
        lengthComboBox.getSelectionModel().select(PasswordLength.THIRTY_TWO);
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
