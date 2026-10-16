// Copyright © 2021-2026 Petr Panteleyev
// SPDX-License-Identifier: BSD-2-Clause
package org.panteleyev.passwdgen

import org.panteleyev.passwdgen.Shortcuts.CTRL_C
import org.panteleyev.passwdgen.Shortcuts.CTRL_G
import org.panteleyev.passwdgen.Shortcuts.CTRL_L
import org.panteleyev.passwdgen.Shortcuts.CTRL_M
import org.panteleyev.passwdgen.Shortcuts.CTRL_P
import org.panteleyev.passwdgen.Shortcuts.CTRL_U
import org.panteleyev.passwdgen.Util.COLON
import org.panteleyev.passwdgen.Util.uiString
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_AVOID_AMBIGUOUS
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_COPY
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_DIGITS
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_EDIT
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_EXIT
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_FILE
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_GENERATE
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_LENGTH
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_LONG_PASSWORD
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_LOWER_CASE
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_MEDIUM_PASSWORD
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_OPTIONS
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_PASSWORD
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_PRESETS
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_SYMBOLS
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_TITLE
import org.panteleyev.passwdgen.bundles.Internationalization.I18N_UPPER_CASE
import org.panteleyev.passwdgen.bundles.Internationalization.UI
import org.panteleyev.password.PasswordCharacterSet
import org.panteleyev.password.PasswordGenerator
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.DefaultComboBoxModel
import javax.swing.ImageIcon
import javax.swing.JCheckBox
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.JTextField
import javax.swing.border.EmptyBorder
import kotlin.system.exitProcess

class GeneratorController : JFrame() {
    private val upperCaseCheckBox = JCheckBox(uiString(UI, I18N_UPPER_CASE))
    private val lowerCaseCheckBox = JCheckBox(uiString(UI, I18N_LOWER_CASE))
    private val digitsCheckBox = JCheckBox(uiString(UI, I18N_DIGITS))
    private val symbolsCheckBox = JCheckBox(uiString(UI, I18N_SYMBOLS))

    private val characterSetCheckBoxes = listOf(
        upperCaseCheckBox, lowerCaseCheckBox, digitsCheckBox, symbolsCheckBox
    )

    private val charsetMap = mapOf(
        upperCaseCheckBox.text to PasswordCharacterSet.UPPER_CASE_LETTERS,
        lowerCaseCheckBox.text to PasswordCharacterSet.LOWER_CASE_LETTERS,
        digitsCheckBox.text to PasswordCharacterSet.DIGITS,
        symbolsCheckBox.text to PasswordCharacterSet.SYMBOLS
    )

    private val avoidAmbiguousLettersCheckBox = JCheckBox(uiString(UI, I18N_AVOID_AMBIGUOUS)).apply {
        alignmentX = 0.0f
        border = EmptyBorder(5, 5, 5, 5)
    }

    private val passwdField = JTextField(40).apply {
        isEditable = false
        inputMap.put(CTRL_C, "none")
    }

    private val lengthComboBox = JComboBox<PasswordLength>().apply {
        model = DefaultComboBoxModel(PasswordLength.entries.toTypedArray())
        maximumSize = minimumSize
    }

    private val generator = PasswordGenerator()

    init {
        title = uiString(UI, I18N_TITLE)
        defaultCloseOperation = EXIT_ON_CLOSE
        jMenuBar = createMenuBar()
        iconImage = getIcon().image

        with(contentPane) {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            add(Box.createVerticalStrut(5))
            add(JPanel().apply {
                border = BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(uiString(UI, I18N_PASSWORD)),
                    EmptyBorder(5, 5, 5, 5)
                )
                alignmentX = 0.0f
                add(passwdField)
            })
            add(Box.createVerticalStrut(5))
            add(JPanel().apply {
                border = BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(uiString(UI, I18N_OPTIONS)),
                    EmptyBorder(5, 5, 5, 5)
                )
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                alignmentX = 0.0f

                add(JPanel().apply {
                    layout = BoxLayout(this, BoxLayout.X_AXIS)
                    alignmentX = 0.0f
                    border = EmptyBorder(5, 5, 5, 5)
                    add(upperCaseCheckBox)
                    add(Box.createHorizontalStrut(10))
                    add(lowerCaseCheckBox)
                    add(Box.createHorizontalStrut(10))
                    add(digitsCheckBox)
                    add(Box.createHorizontalStrut(10))
                    add(symbolsCheckBox)
                    add(Box.createHorizontalGlue())
                })
                add(JPanel().apply {
                    layout = BoxLayout(this, BoxLayout.X_AXIS)
                    alignmentX = 0.0f
                    border = EmptyBorder(5, 5, 5, 5)
                    add(JLabel(uiString(UI, I18N_LENGTH, COLON)))
                    add(Box.createHorizontalStrut(5))
                    add(lengthComboBox)
                    add(Box.createHorizontalGlue())
                })
                add(avoidAmbiguousLettersCheckBox)
            })
            add(Box.createVerticalStrut(5))
        }

        setInitialState()

        isResizable = false
        pack()
        setLocationRelativeTo(null)
    }

    private fun createMenuBar() = JMenuBar().apply {
        add(JMenu(uiString(UI, I18N_FILE)).apply {
            add(JMenuItem(uiString(UI, I18N_GENERATE)).apply {
                accelerator = CTRL_G
                addActionListener { onGenerate() }
            })
            add(JSeparator())
            add(JMenuItem(uiString(UI, I18N_EXIT)).apply {
                addActionListener { onExit() }
            })
        })
        add(JMenu(uiString(UI, I18N_EDIT)).apply {
            add(JMenuItem(uiString(UI, I18N_COPY)).apply {
                accelerator = CTRL_C
                addActionListener { onCopy() }
            })
        })
        add(JMenu(uiString(UI, I18N_PRESETS)).apply {
            add(JMenuItem(uiString(UI, I18N_MEDIUM_PASSWORD)).apply {
                accelerator = CTRL_M
                addActionListener { onMediumPassword() }
            })
            add(JMenuItem(uiString(UI, I18N_LONG_PASSWORD)).apply {
                accelerator = CTRL_L
                addActionListener { onLongPassword() }
            })
            add(JSeparator())
            add(JMenuItem("Unix").apply {
                accelerator = CTRL_U
                addActionListener { onUnix() }
            })
            add(JMenuItem("PIN").apply {
                accelerator = CTRL_P
                addActionListener { onPin() }
            })
        })
    }

    private fun setInitialState() {
        avoidAmbiguousLettersCheckBox.isSelected = true
        upperCaseCheckBox.isSelected = true
        lowerCaseCheckBox.isSelected = true
        digitsCheckBox.isSelected = true
        lengthComboBox.selectedItem = PasswordLength.THIRTY_TWO
    }

    private fun onUnix() {
        upperCaseCheckBox.isSelected = true
        lowerCaseCheckBox.isSelected = true
        digitsCheckBox.isSelected = true
        symbolsCheckBox.isSelected = true
        lengthComboBox.selectedItem = PasswordLength.EIGHT
        onGenerate()
    }

    private fun onPin() {
        upperCaseCheckBox.isSelected = false
        lowerCaseCheckBox.isSelected = false
        digitsCheckBox.isSelected = true
        symbolsCheckBox.isSelected = false
        lengthComboBox.selectedItem = PasswordLength.FOUR
        onGenerate()
    }

    private fun onMediumPassword() {
        upperCaseCheckBox.isSelected = true
        lowerCaseCheckBox.isSelected = true
        digitsCheckBox.isSelected = true
        symbolsCheckBox.isSelected = true
        lengthComboBox.selectedItem = PasswordLength.SIXTEEN
        onGenerate()
    }

    private fun onLongPassword() {
        upperCaseCheckBox.isSelected = true
        lowerCaseCheckBox.isSelected = true
        digitsCheckBox.isSelected = true
        symbolsCheckBox.isSelected = true
        lengthComboBox.selectedItem = PasswordLength.THIRTY_TWO
        onGenerate()
    }

    private fun onGenerate() {
        val characterSets = characterSetCheckBoxes
            .filter { it.isSelected }
            .mapNotNull { charsetMap[it.text] }
            .toSet()

        passwdField.text = generator.generate(
            characterSets,
            (lengthComboBox.selectedItem as PasswordLength).length,
            !avoidAmbiguousLettersCheckBox.isSelected
        )
    }

    private fun getIcon() = ImageIcon(javaClass.getResource("/icon.png"))

    private fun onCopy() {
        val cb = Toolkit.getDefaultToolkit().systemClipboard
        val ct = StringSelection(passwdField.text)
        cb.setContents(ct, null)
    }

    private fun onExit(): Nothing = exitProcess(0)
}