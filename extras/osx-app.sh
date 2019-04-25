#!/bin/sh

rm -rf target/dist

$JPACKAGE_HOME/bin/jpackage \
    --module password.generator/org.panteleyev.passwdgen.PasswordGeneratorApplication \
    --runtime-image $JAVA_HOME \
    --verbose \
    --dest target/dist \
    -p target/jmods \
    --java-options "-Dfile.encoding=UTF-8" \
    --icon icons/icons.icns \
    --name "Password Generator" \
    --app-version 20.1.0 \
    --vendor panteleyev.org
