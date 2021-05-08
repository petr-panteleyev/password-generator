/*
 Copyright (c) Petr Panteleyev. All rights reserved.
 Licensed under the BSD license. See LICENSE file in the project root for full license information.
 */
package org.panteleyev.passwdgen

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
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
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import org.panteleyev.passwdgen.PasswordGeneratorApplication.Companion.RB
import kotlin.system.exitProcess

class GeneratorController : BorderPane() {
    private val digitsCheckBox = CheckBox(RB.getString("digits"))
    private val upperCaseCheckBox = CheckBox(RB.getString("upperCase"))
    private val lowerCaseCheckBox = CheckBox(RB.getString("lowerCase"))
    private val symbolsCheckBox = CheckBox(RB.getString("symbols"))
    private val avoidAmbiguousLettersCheckBox = CheckBox(RB.getString("avoid"))
    private val passwdField = TextField()
    private val lengthComboBox = ComboBox<PasswordLength>()

    init {
        val hBox = HBox(
            upperCaseCheckBox,
            lowerCaseCheckBox,
            digitsCheckBox,
            symbolsCheckBox
        ).also {
            it.alignment = Pos.CENTER_LEFT
        }

        val m5 = Insets(0.0, 0.0, 0.0, 5.0)
        HBox.setMargin(digitsCheckBox, m5)
        HBox.setMargin(lowerCaseCheckBox, m5)
        HBox.setMargin(symbolsCheckBox, m5)

        val lenHBox = HBox(
            Label(RB.getString("length")),
            lengthComboBox
        ).also {
            it.alignment = Pos.CENTER_LEFT
        }

        HBox.setMargin(lengthComboBox, Insets(0.0, 0.0, 0.0, 3.0))

        val topInsets = Insets(10.0, 0.0, 0.0, 0.0)
        VBox.setMargin(lenHBox, topInsets)
        VBox.setMargin(avoidAmbiguousLettersCheckBox, topInsets)
        avoidAmbiguousLettersCheckBox.isSelected = true

        val vBox = VBox(
            TitledPane(
                RB.getString("password"),
                BorderPane(passwdField)
            ),
            TitledPane(
                RB.getString("options"),
                VBox(
                    hBox,
                    lenHBox,
                    avoidAmbiguousLettersCheckBox
                )
            )
        )

        top = createMenuBar()
        center = vBox

        // Initial state
        lengthComboBox.items.addAll(*PasswordLength.values())
        lengthComboBox.selectionModel.select(PasswordLength.THIRTY_TWO)
        upperCaseCheckBox.isSelected = true
        lowerCaseCheckBox.isSelected = true
        digitsCheckBox.isSelected = true
    }

    private fun createMenuBar() = MenuBar(
        Menu(
            RB.getString("menuFile"), null,
            MenuItem(RB.getString("menuGenerate")).also {
                it.onAction = EventHandler { onGenerate() }
                it.accelerator = KeyCodeCombination(KeyCode.G, KeyCombination.SHORTCUT_DOWN)
            },
            SeparatorMenuItem(),
            MenuItem(RB.getString("menuExit")).also {
                it.onAction = EventHandler { onExit() }
            }
        ),
        Menu(
            RB.getString("menuEdit"), null,
            MenuItem(RB.getString("menuCopy")).also {
                it.onAction = EventHandler { onCopy() }
                it.accelerator = KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN)
            }
        ),
        Menu(
            RB.getString("presets"), null,
            MenuItem(RB.getString("medium_password")).also {
                it.onAction = EventHandler { onMediumPassword() }
                it.accelerator = KeyCodeCombination(KeyCode.M, KeyCombination.SHORTCUT_DOWN)
            },
            MenuItem(RB.getString("long_password")).also {
                it.onAction = EventHandler { onLongPassword() }
                it.accelerator = KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN)
            },
            SeparatorMenuItem(),
            MenuItem("Unix").also {
                it.onAction = EventHandler { onUnix() }
                it.accelerator = KeyCodeCombination(KeyCode.U, KeyCombination.SHORTCUT_DOWN)
            },
            MenuItem("PIN").also {
                it.onAction = EventHandler { onPin() }
                it.accelerator = KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN)
            }
        )
    ).also {
        it.isUseSystemMenuBar = true
    }

    private fun onUnix() {
        upperCaseCheckBox.isSelected = true
        lowerCaseCheckBox.isSelected = true
        digitsCheckBox.isSelected = true
        symbolsCheckBox.isSelected = true
        lengthComboBox.selectionModel.select(PasswordLength.EIGHT)
        onGenerate()
    }

    private fun onPin() {
        upperCaseCheckBox.isSelected = false
        lowerCaseCheckBox.isSelected = false
        digitsCheckBox.isSelected = true
        symbolsCheckBox.isSelected = false
        lengthComboBox.selectionModel.select(PasswordLength.FOUR)
        onGenerate()
    }

    private fun onMediumPassword() {
        upperCaseCheckBox.isSelected = true
        lowerCaseCheckBox.isSelected = true
        digitsCheckBox.isSelected = true
        symbolsCheckBox.isSelected = true
        lengthComboBox.selectionModel.select(PasswordLength.SIXTEEN)
        onGenerate()
    }

    private fun onLongPassword() {
        upperCaseCheckBox.isSelected = true
        lowerCaseCheckBox.isSelected = true
        digitsCheckBox.isSelected = true
        symbolsCheckBox.isSelected = true
        lengthComboBox.selectionModel.select(PasswordLength.THIRTY_TWO)
        onGenerate()
    }

    private fun onGenerate() {
        passwdField.text = Generator.generate(
            upperCase = upperCaseCheckBox.isSelected,
            lowerCase = lowerCaseCheckBox.isSelected,
            digits = digitsCheckBox.isSelected,
            symbols = symbolsCheckBox.isSelected,
            noAmbiguousLetters = avoidAmbiguousLettersCheckBox.isSelected,
            length = lengthComboBox.selectionModel.selectedItem
        )
    }

    private fun onCopy() {
        val cb = Clipboard.getSystemClipboard()
        val ct = ClipboardContent()
        ct.putString(passwdField.text)
        cb.setContent(ct)
    }

    private fun onExit() {
        exitProcess(0)
    }
}