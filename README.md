# Password Generator

Simple password generator for desktop platforms like OS X, Linux or Windows.

![screenshot](docs/screenshot.png)

## Presets

There are several presets called by the corresponding menu item:

|Preset|Upper Case|Lower Case|Digits|Symbols|Length|
|---|---|---|---|---|---|
|Long|X|X|X|X|32|
|Medium|X|X|X|X|16|
|UNIX|X|X|X|X|8|
|PIN| | |X| |4|

## Ambiguous Letters

This options excludes characters that may look confusing depending on font: 'I', 'l', 'O'.

## Build

Set ```JAVA_HOME``` to JDK 19.

```shell script
$ ./gradlew clean build
```

## Run

```
./gradlew run
```

## Binary Packages

To build binary installers perform the following steps:
* On Microsoft Windows: install [WiX Toolset](https://wixtoolset.org/releases/), add its binary directory to ```PATH``` 
environment variable
* Execute the following commands:
```shell script
$ ./gradlew clean build jpackage
```

Installation packages will be found in ```build/dist``` directory.

## Support

There is no support for this application.
