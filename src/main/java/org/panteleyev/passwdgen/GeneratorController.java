/*
 * Copyright (c) 2012, 2017, Petr Panteleyev <petr@panteleyev.org>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.panteleyev.passwdgen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * FXML Controller class for Generator window
 *
 * @author Petr Panteleyev
 */
public class GeneratorController implements Initializable {
    private final Generator             g = new Generator();

    @FXML private CheckBox              digitsCheckBox;
    @FXML private CheckBox              upperCaseCheckBox;
    @FXML private CheckBox              lowerCaseCheckBox;
    @FXML private CheckBox              symbolsCheckBox;
    @FXML private CheckBox              avoidAmbiguousLettesCheckBox;
    @FXML private TextField             passwdField;
    @FXML private ComboBox<Integer>     lengthComboBox;
    @FXML private MenuBar               menuBar;
    @FXML private MenuItem              genItem;
    @FXML private MenuItem              copyItem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuBar.setUseSystemMenuBar(true);

        lengthComboBox.getItems().clear();
        lengthComboBox.getItems().addAll(4, 6, 8, 16, 24, 32);
        lengthComboBox.getSelectionModel().select(2);
        genItem.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.SHORTCUT_DOWN));
        copyItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));

        // bindings
        g.useDigitsProperty().bind(digitsCheckBox.selectedProperty());
        g.useLowerCaseProperty().bind(lowerCaseCheckBox.selectedProperty());
        g.useSymbolsProperty().bind(symbolsCheckBox.selectedProperty());
        g.useUpperCaseProperty().bind(upperCaseCheckBox.selectedProperty());
        g.avoidAmbiguousLettersProperty().bind(avoidAmbiguousLettesCheckBox.selectedProperty());
        g.lengthProperty().bind(lengthComboBox.getSelectionModel().selectedItemProperty());
        passwdField.textProperty().bind(g.passwordProperty());
    }

    public void onUnixButtonPressed() {
        upperCaseCheckBox.setSelected(true);
        lowerCaseCheckBox.setSelected(true);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(false);
        lengthComboBox.getSelectionModel().select(2);
        onGenerate();
    }

    public void onPinButtonPressed() {
        upperCaseCheckBox.setSelected(false);
        lowerCaseCheckBox.setSelected(false);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(false);
        avoidAmbiguousLettesCheckBox.setSelected(false);
        lengthComboBox.getSelectionModel().select(0);
        onGenerate();
    }

    public void onGenerate() {
        g.generate();
    }

    public void onCopy() {
        Clipboard cb = Clipboard.getSystemClipboard();
        ClipboardContent ct = new ClipboardContent();
        ct.putString(passwdField.getText());
        cb.setContent(ct);
    }


    public void onTest() {
        System.exit(0);
    }

    public void onExit() {
        System.exit(0);
    }
}
