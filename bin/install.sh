#!/bin/sh

if [ -z "$1" ]
then
  echo "Usage: sudo install.sh <install dir>"
  exit
fi

LAUNCH_DIR=$(cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd)
INSTALL_DIR=$1/password-generator

echo -n "Installing into $INSTALL_DIR..."
mkdir -p $INSTALL_DIR
rm -rf $INSTALL_DIR/*
cp $LAUNCH_DIR/../icons/icon.png $INSTALL_DIR
cp -r $LAUNCH_DIR/../target/jlink/* $INSTALL_DIR

echo "
#!/bin/sh
$INSTALL_DIR/bin/java \\
  -Xms100m \\
  -Xmx100m \\
  -XX:+AutoCreateSharedArchive \\
  -XX:SharedArchiveFile=\$TMP/password-generator.jsa \\
  --module password.generator/org.panteleyev.passwdgen.PasswordGeneratorApplication
" > $INSTALL_DIR/password-generator.sh

chmod +x $INSTALL_DIR/password-generator.sh

echo "[Desktop Entry]
Type=Application
Version=1.5
Name=Password Generator
Name[ru_RU]=Генератор паролей
Comment=Application to generate passwords
Comment[ru_RU]=Приложение для генерации паролей
Icon=$INSTALL_DIR/icon.png
Exec=/bin/sh $INSTALL_DIR/password-generator.sh
Categories=Utility;Java;
" > $INSTALL_DIR/password-generator.desktop

echo "done"
