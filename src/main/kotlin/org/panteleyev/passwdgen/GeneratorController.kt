/*
 * Copyright (c) 2017, Petr Panteleyev <petr@panteleyev.org>
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
 */

package org.panteleyev.passwdgen

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.control.TextField
import javafx.scene.control.TitledPane
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.util.ResourceBundle

internal class GeneratorController : BorderPane() {
    private val rb = ResourceBundle.getBundle(PasswordGeneratorApplication.BUNDLE_PATH)

    private val digitsCheckBox = CheckBox(rb.getString("digits"))
    private val upperCaseCheckBox = CheckBox(rb.getString("upperCase"))
    private val lowerCaseCheckBox = CheckBox(rb.getString("lowerCase"))
    private val symbolsCheckBox = CheckBox(rb.getString("symbols"))
    private val avoidAmbiguousLettesCheckBox = CheckBox(rb.getString("avoid"))
    private val passwdField = TextField()
    private val lengthComboBox = ComboBox<Int>()

    init {
        // Main menu

        val genItem = MenuItem(rb.getString("menuGenerate"))
        genItem.setOnAction { onGenerate() }
        genItem.accelerator = KeyCodeCombination(KeyCode.G, KeyCombination.SHORTCUT_DOWN)

        val exitItem = MenuItem(rb.getString("menuExit"))
        exitItem.setOnAction { onExit() }

        val copyItem = MenuItem(rb.getString("menuCopy"))
        copyItem.setOnAction { onCopy() }
        copyItem.accelerator = KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN)

        val menuBar = MenuBar(
                Menu(rb.getString("menuFile"), null,
                        genItem, SeparatorMenuItem(), exitItem),
                Menu(rb.getString("menuEdit"), null,
                        copyItem)
        )

        menuBar.isUseSystemMenuBar = true

        top = menuBar

        val p1 = BorderPane()
        p1.center = passwdField

        val lenLabel = Label(rb.getString("length"))
        val hBox = HBox(
                upperCaseCheckBox,
                lowerCaseCheckBox,
                digitsCheckBox,
                symbolsCheckBox,
                lenLabel,
                lengthComboBox
        )
        hBox.alignment = Pos.CENTER_LEFT

        val m5 = Insets(0.0, 0.0, 0.0, 5.0)
        HBox.setMargin(digitsCheckBox, m5)
        HBox.setMargin(lowerCaseCheckBox, m5)
        HBox.setMargin(symbolsCheckBox, m5)
        HBox.setMargin(lenLabel, m5)
        HBox.setMargin(lengthComboBox, Insets(0.0, 0.0, 0.0, 3.0))

        val unixButton = Button("UNIX")
        unixButton.setOnAction { onUnixButtonPressed() }
        val pinButton = Button("PIN")
        pinButton.setOnAction { onPinButtonPressed() }

        val flow = FlowPane(
                unixButton, pinButton,
                avoidAmbiguousLettesCheckBox
        )

        FlowPane.setMargin(pinButton, Insets(0.0, 0.0, 0.0, 10.0))
        FlowPane.setMargin(avoidAmbiguousLettesCheckBox, Insets(0.0, 0.0, 0.0, 20.0))

        val vBox = VBox(
                TitledPane(rb.getString("password"), p1),
                TitledPane("Options", hBox),
                TitledPane("Presets", flow)
        )

        center = vBox

        // Initial state
        lengthComboBox.items.addAll(4, 6, 8, 16, 24, 32)
        lengthComboBox.selectionModel.clearAndSelect(2)
        upperCaseCheckBox.isSelected = true
        lowerCaseCheckBox.isSelected = true
        digitsCheckBox.isSelected = true

        // bindings
        Generator.useDigitsProperty.bind(digitsCheckBox.selectedProperty())
        Generator.useLowerCaseProperty.bind(lowerCaseCheckBox.selectedProperty())
        Generator.useSymbolsProperty.bind(symbolsCheckBox.selectedProperty())
        Generator.useUpperCaseProperty.bind(upperCaseCheckBox.selectedProperty())
        Generator.avoidAmbiguousLettersProperty.bind(avoidAmbiguousLettesCheckBox.selectedProperty())
        Generator.lengthProperty.bind(lengthComboBox.selectionModel.selectedItemProperty())
        passwdField.textProperty().bind(Generator.passwordProperty)
    }

    private fun onUnixButtonPressed() {
        upperCaseCheckBox.isSelected = true
        lowerCaseCheckBox.isSelected = true
        digitsCheckBox.isSelected = true
        symbolsCheckBox.isSelected = false
        lengthComboBox.selectionModel.clearAndSelect(2)
        onGenerate()
    }

    private fun onPinButtonPressed() {
        upperCaseCheckBox.isSelected = false
        lowerCaseCheckBox.isSelected = false
        digitsCheckBox.isSelected = true
        symbolsCheckBox.isSelected = false
        avoidAmbiguousLettesCheckBox.isSelected = false
        lengthComboBox.selectionModel.clearAndSelect(0)
        onGenerate()
    }

    private fun onGenerate() {
        Generator.generate()
    }

    private fun onCopy() {
        val cb = Clipboard.getSystemClipboard()
        val ct = ClipboardContent()
        ct.putString(passwdField.text)
        cb.setContent(ct)
    }

    private fun onExit() {
        System.exit(0)
    }
}
