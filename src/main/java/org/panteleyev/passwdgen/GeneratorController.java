/*
 Copyright © 2021-2025 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
package org.panteleyev.passwdgen;

import org.panteleyev.commons.password.PasswordCharacterSet;
import org.panteleyev.commons.password.PasswordGenerator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.swing.BoxLayout.Y_AXIS;
import static org.panteleyev.passwdgen.PasswordGeneratorApplication.UI;
import static org.panteleyev.passwdgen.Shortcuts.CTRL_C;
import static org.panteleyev.passwdgen.Shortcuts.CTRL_G;
import static org.panteleyev.passwdgen.Shortcuts.CTRL_L;
import static org.panteleyev.passwdgen.Shortcuts.CTRL_M;
import static org.panteleyev.passwdgen.Shortcuts.CTRL_P;
import static org.panteleyev.passwdgen.Shortcuts.CTRL_U;
import static org.panteleyev.passwdgen.Util.COLON;
import static org.panteleyev.passwdgen.Util.uiString;
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
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_TITLE;
import static org.panteleyev.passwdgen.bundles.Internationalization.I18N_UPPER_CASE;

public class GeneratorController extends JFrame {

    private final JCheckBox upperCaseCheckBox = new JCheckBox(uiString(UI, I18N_UPPER_CASE));
    private final JCheckBox lowerCaseCheckBox = new JCheckBox(uiString(UI, I18N_LOWER_CASE));
    private final JCheckBox digitsCheckBox = new JCheckBox(uiString(UI, I18N_DIGITS));
    private final JCheckBox symbolsCheckBox = new JCheckBox(uiString(UI, I18N_SYMBOLS));
    private final List<JCheckBox> characterSetCheckBoxes = List.of(
            upperCaseCheckBox, lowerCaseCheckBox, digitsCheckBox, symbolsCheckBox
    );
    private final Map<String, PasswordCharacterSet> charsetMap = Map.of(
            upperCaseCheckBox.getText(), PasswordCharacterSet.UPPER_CASE_LETTERS,
            lowerCaseCheckBox.getText(), PasswordCharacterSet.LOWER_CASE_LETTERS,
            digitsCheckBox.getText(), PasswordCharacterSet.DIGITS,
            symbolsCheckBox.getText(), PasswordCharacterSet.SYMBOLS
    );

    private final JCheckBox avoidAmbiguousLettersCheckBox = new JCheckBox(uiString(UI, I18N_AVOID_AMBIGUOUS));
    private final JTextField passwdField = new JTextField(40);
    private final JComboBox<PasswordLength> lengthComboBox = new JComboBox<>();

    private final PasswordGenerator generator = new PasswordGenerator();

    public GeneratorController() {
        setTitle(uiString(UI, I18N_TITLE));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());
        setIconImage(getIcon().getImage());

        passwdField.setEditable(false);
        passwdField.getInputMap().put(CTRL_C, "none");

        var container = getContentPane();
        container.setLayout(new BoxLayout(container, Y_AXIS));

        var passwordPanel = new JPanel();
        passwordPanel.setBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(uiString(UI, I18N_PASSWORD)),
                        new EmptyBorder(5, 5, 5, 5)));
        passwordPanel.add(passwdField);
        passwordPanel.setAlignmentX(0);

        var optionsPanel = new JPanel();
        optionsPanel.setBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(uiString(UI, I18N_OPTIONS)),
                        new EmptyBorder(5, 5, 5, 5)));
        optionsPanel.setLayout(new BoxLayout(optionsPanel, Y_AXIS));
        optionsPanel.setAlignmentX(0);

        var lengthPanel = new JPanel();
        lengthPanel.setLayout(new BoxLayout(lengthPanel, BoxLayout.X_AXIS));
        lengthPanel.add(new JLabel(uiString(UI, I18N_LENGTH, COLON)));
        lengthPanel.add(Box.createHorizontalStrut(5));
        lengthPanel.add(lengthComboBox);
        lengthPanel.add(Box.createHorizontalGlue());
        lengthPanel.setAlignmentX(0);
        lengthPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        var checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.X_AXIS));
        checkBoxPanel.add(upperCaseCheckBox);
        checkBoxPanel.add(Box.createHorizontalStrut(10));
        checkBoxPanel.add(lowerCaseCheckBox);
        checkBoxPanel.add(Box.createHorizontalStrut(10));
        checkBoxPanel.add(digitsCheckBox);
        checkBoxPanel.add(Box.createHorizontalStrut(10));
        checkBoxPanel.add(symbolsCheckBox);
        checkBoxPanel.add(Box.createHorizontalGlue());
        checkBoxPanel.setAlignmentX(0);
        checkBoxPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        avoidAmbiguousLettersCheckBox.setBorder(new EmptyBorder(5, 5, 5, 5));

        optionsPanel.add(checkBoxPanel);
        optionsPanel.add(lengthPanel);
        optionsPanel.add(avoidAmbiguousLettersCheckBox);

        container.add(Box.createVerticalStrut(5));
        container.add(passwordPanel);
        container.add(Box.createVerticalStrut(5));
        container.add(optionsPanel);
        container.add(Box.createVerticalStrut(5));
        avoidAmbiguousLettersCheckBox.setAlignmentX(0);

        // Initial state
        avoidAmbiguousLettersCheckBox.setSelected(true);

        lengthComboBox.setModel(new DefaultComboBoxModel<>(PasswordLength.values()));
        lengthComboBox.setSelectedItem(PasswordLength.THIRTY_TWO);
        lengthComboBox.setMaximumSize(lengthComboBox.getMinimumSize());

        upperCaseCheckBox.setSelected(true);
        lowerCaseCheckBox.setSelected(true);
        digitsCheckBox.setSelected(true);

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();

        var fileMenu = new JMenu(uiString(UI, I18N_FILE));
        var generateMenuItem = new JMenuItem(uiString(UI, I18N_GENERATE));
        generateMenuItem.addActionListener(_ -> onGenerate());
        generateMenuItem.setAccelerator(CTRL_G);
        var exitMenuItem = new JMenuItem(uiString(UI, I18N_EXIT));
        exitMenuItem.addActionListener(_ -> onExit());
        fileMenu.add(generateMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(exitMenuItem);

        var editMenu = new JMenu(uiString(UI, I18N_EDIT));
        var copyMenuItem = new JMenuItem(uiString(UI, I18N_COPY));
        copyMenuItem.addActionListener(_ -> onCopy());
        copyMenuItem.setAccelerator(CTRL_C);
        editMenu.add(copyMenuItem);

        var presetsMenu = new JMenu(uiString(UI, I18N_PRESETS));
        var mediumPasswordMenuItem = new JMenuItem(uiString(UI, I18N_MEDIUM_PASSWORD));
        mediumPasswordMenuItem.addActionListener(_ -> onMediumPassword());
        mediumPasswordMenuItem.setAccelerator(CTRL_M);
        var longPasswordMenuItem = new JMenuItem(uiString(UI, I18N_LONG_PASSWORD));
        longPasswordMenuItem.addActionListener(_ -> onLongPassword());
        longPasswordMenuItem.setAccelerator(CTRL_L);
        var unixMenuItem = new JMenuItem("Unix");
        unixMenuItem.addActionListener(_ -> onUnix());
        unixMenuItem.setAccelerator(CTRL_U);
        var pinMenuItem = new JMenuItem("PIN");
        pinMenuItem.addActionListener(_ -> onPin());
        pinMenuItem.setAccelerator(CTRL_P);
        presetsMenu.add(mediumPasswordMenuItem);
        presetsMenu.add(longPasswordMenuItem);
        presetsMenu.add(new JSeparator());
        presetsMenu.add(unixMenuItem);
        presetsMenu.add(pinMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(presetsMenu);
        return menuBar;
    }

    private void onUnix() {
        upperCaseCheckBox.setSelected(true);
        lowerCaseCheckBox.setSelected(true);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(true);
        lengthComboBox.setSelectedItem(PasswordLength.EIGHT);
        onGenerate();
    }

    private void onPin() {
        upperCaseCheckBox.setSelected(false);
        lowerCaseCheckBox.setSelected(false);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(false);
        lengthComboBox.setSelectedItem(PasswordLength.FOUR);
        onGenerate();
    }

    private void onMediumPassword() {
        upperCaseCheckBox.setSelected(true);
        lowerCaseCheckBox.setSelected(true);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(true);
        lengthComboBox.setSelectedItem(PasswordLength.SIXTEEN);
        onGenerate();
    }

    private void onLongPassword() {
        upperCaseCheckBox.setSelected(true);
        lowerCaseCheckBox.setSelected(true);
        digitsCheckBox.setSelected(true);
        symbolsCheckBox.setSelected(true);
        lengthComboBox.setSelectedItem(PasswordLength.THIRTY_TWO);
        onGenerate();
    }

    private void onGenerate() {
        var characterSets = characterSetCheckBoxes.stream()
                .filter(JCheckBox::isSelected)
                .map(checkBox -> charsetMap.get(checkBox.getText()))
                .collect(Collectors.toSet());

        passwdField.setText(generator.generate(
                characterSets,
                ((PasswordLength) lengthComboBox.getSelectedItem()).getLength(),
                !avoidAmbiguousLettersCheckBox.isSelected()
        ));
    }

    private ImageIcon getIcon() {
        return new ImageIcon(this.getClass().getResource("/icon.png"));
    }

    private void onCopy() {
        var cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        var ct = new StringSelection(passwdField.getText());
        cb.setContents(ct, null);
    }

    private void onExit() {
        System.exit(0);
    }
}
