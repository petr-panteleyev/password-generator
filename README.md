# Password Generator

Simple password generator for desktop platforms like OS X, Linux or Windows.

![screenshot](docs/screenshot.png)

## Presets

There are two presets called by the corresponding button:

* UNIX - 8 letters and digits
* PIN - 4 digits

## Ambiguous Letters

This options excludes characters that may look confusing depending on font: 'I', 'l', 'O', '0'.

## Running

```
java --module-path <path> -Dfile.encoding=UTF-8 -m password.generator/org.panteleyev.passwdgen.PasswordGeneratorApplication
```
