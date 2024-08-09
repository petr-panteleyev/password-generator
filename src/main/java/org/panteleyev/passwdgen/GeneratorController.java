/*
 Copyright © 2021-2024 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
package org.panteleyev.passwdgen;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.panteleyev.commons.password.PasswordCharacterSet;
import org.panteleyev.commons.password.PasswordGenerator;

import java.util.List;
import java.util.stream.Collectors;

import static org.panteleyev.fx.BoxFactory.hBox;
import static org.panteleyev.fx.FxUtils.COLON;
import static org.panteleyev.fx.FxUtils.fxString;
import static org.panteleyev.fx.LabelFactory.label;
import static org.panteleyev.fx.MenuFactory.menu;
import static org.panteleyev.fx.MenuFactory.menuItem;
import static org.panteleyev.passwdgen.PasswordGeneratorApplication.UI;
import static org.panteleyev.passwdgen.Shortcuts.SHORTCUT_C;
import static org.panteleyev.passwdgen.Shortcuts.SHORTCUT_G;
import static org.panteleyev.passwdgen.Shortcuts.SHORTCUT_L;
import static org.panteleyev.passwdgen.Shortcuts.SHORTCUT_M;
import static org.panteleyev.passwdgen.Shortcuts.SHORTCUT_P;
import static org.panteleyev.passwdgen.Shortcuts.SHORTCUT_U;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_AVOID_AMBIGUOUS;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_COPY;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_DIGITS;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_EDIT;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_EXIT;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_FILE;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_GENERATE;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_LENGTH;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_LONG_PASSWORD;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_LOWER_CASE;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_MEDIUM_PASSWORD;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_OPTIONS;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_PASSWORD;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_PRESETS;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_SYMBOLS;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_UPPER_CASE;

class GeneratorController extends BorderPane {
    private final CheckBox upperCaseCheckBox =
            characterSetCheckBox(I18N_UPPER_CASE, PasswordCharacterSet.UPPER_CASE_LETTERS);
    private final CheckBox lowerCaseCheckBox =
            characterSetCheckBox(I18N_LOWER_CASE, PasswordCharacterSet.LOWER_CASE_LETTERS);
    private final CheckBox digitsCheckBox =
            characterSetCheckBox(I18N_DIGITS, PasswordCharacterSet.DIGITS);
    private final CheckBox symbolsCheckBox =
            characterSetCheckBox(I18N_SYMBOLS, PasswordCharacterSet.SYMBOLS);
    private final List<CheckBox> characterSetCheckBoxes = List.of(
            upperCaseCheckBox, lowerCaseCheckBox, digitsCheckBox, symbolsCheckBox
    );

    private final CheckBox avoidAmbiguousLettersCheckBox = new CheckBox(fxString(UI, I18N_AVOID_AMBIGUOUS));
    private final TextField passwdField = new TextField();
    private final ComboBox<PasswordLength> lengthComboBox = new ComboBox<>();

    private final PasswordGenerator generator = new PasswordGenerator();

    public GeneratorController() {
        setTop(createMenuBar());
        setCenter(new VBox(
                new TitledPane(fxString(UI, I18N_PASSWORD), new BorderPane(passwdField)),
                new TitledPane(fxString(UI, I18N_OPTIONS),
                        new VBox(10,
                                hBox(List.of(upperCaseCheckBox, lowerCaseCheckBox, digitsCheckBox, symbolsCheckBox),
                                        box -> {
                                            box.setAlignment(Pos.CENTER_LEFT);
                                            box.setSpacing(10);
                                        }),
                                hBox(List.of(label(fxString(UI, I18N_LENGTH, COLON)), lengthComboBox),
                                        box -> {
                                            box.setAlignment(Pos.CENTER_LEFT);
                                            box.setSpacing(3);
                                        }),
                                avoidAmbiguousLettersCheckBox
                        )
                )
        ));

        // Initial state
        avoidAmbiguousLettersCheckBox.setSelected(true);
        lengthComboBox.getItems().addAll(PasswordLength.values());
        lengthComboBox.getSelectionModel().select(PasswordLength.THIRTY_TWO);
        upperCaseCheckBox.setSelected(true);
        lowerCaseCheckBox.setSelected(true);
        digitsCheckBox.setSelected(true);
    }

    private CheckBox characterSetCheckBox(String key, PasswordCharacterSet characterSet) {
        var checkBox = new CheckBox(fxString(UI, key));
        checkBox.setUserData(characterSet);
        return checkBox;
    }

    private MenuBar createMenuBar() {
        var menuBar = new MenuBar(
                menu(fxString(UI, I18N_FILE),
                        menuItem(fxString(UI, I18N_GENERATE), SHORTCUT_G, _ -> onGenerate()),
                        new SeparatorMenuItem(),
                        menuItem(fxString(UI, I18N_EXIT), _ -> onExit())
                ),
                menu(fxString(UI, I18N_EDIT),
                        menuItem(fxString(UI, I18N_COPY), SHORTCUT_C, _ -> onCopy())
                ),
                menu(fxString(UI, I18N_PRESETS),
                        menuItem(fxString(UI, I18N_MEDIUM_PASSWORD), SHORTCUT_M, _ -> onMediumPassword()),
                        menuItem(fxString(UI, I18N_LONG_PASSWORD), SHORTCUT_L, _ -> onLongPassword()),
                        new SeparatorMenuItem(),
                        menuItem("Unix", SHORTCUT_U, _ -> onUnix()),
                        menuItem("PIN", SHORTCUT_P, _ -> onPin())
                )
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
        var characterSets = characterSetCheckBoxes.stream()
                .filter(CheckBox::isSelected)
                .map(checkBox -> (PasswordCharacterSet) checkBox.getUserData())
                .collect(Collectors.toSet());

        passwdField.setText(generator.generate(
                characterSets,
                lengthComboBox.getSelectionModel().getSelectedItem().getLength(),
                !avoidAmbiguousLettersCheckBox.isSelected()
        ));
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