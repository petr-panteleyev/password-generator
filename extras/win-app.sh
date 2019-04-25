#!/bin/sh

rm -rf target/dist

$JPACKAGE_HOME/bin/jpackage \
    --module password.generator/org.panteleyev.passwdgen.PasswordGeneratorApplication \
    --runtime-image "$JAVA_HOME" \
    --verbose \
    --dest target/dist \
    -p target/jmods \
    --java-options "-Dfile.encoding=UTF-8" \
    --icon icons/icons.ico \
    --name "Password Generator" \
    --app-version 20.1.0 \
    --vendor panteleyev.org \
    --win-menu \
    --win-dir-chooser \
    --win-upgrade-uuid 2e0c0a36-7aab-4d84-a5a1-4d98fa296dad \
    --win-menu-group "panteleyev.org"