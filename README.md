# Password Generator

![JDK](./docs/java-24.png)
[![License](./docs/license.png)](LICENSE)

Simple password generator for desktop platforms like OS X, Linux or Windows.

![screenshot](docs/screenshot.png)

## Presets

There are several presets called by the corresponding menu item:

| Preset | Upper Case | Lower Case | Digits | Symbols | Length |
|--------|------------|------------|--------|---------|--------|
| Long   | X          | X          | X      | X       | 32     |
| Medium | X          | X          | X      | X       | 16     |
| UNIX   | X          | X          | X      | X       | 8      |
| PIN    |            |            | X      |         | 4      |

## Ambiguous Letters

This options excludes characters that may look confusing depending on font: 'I', 'l', 'O'.

## Build

Set ```JAVA_HOME``` to JDK 24+.

```shell
$ ./gradlew clean build
```

## Run

```shell
$ ./gradlew run
```

## Custom Run-Time Image

```shell
./gradlew jlink
```

Run-time image will be found in ```build/jlink``` directory.

## Binary Packages

```shell script
./gradlew jpackage
```

Installation packages will be found in ```build/dist``` directory.

## Support

There is no support for this application.
