# Password Generator

Simple password generator for desktop platforms like OS X, Linux or Windows.

![screenshot](docs/screenshot.png)

## Presets

There are two presets called by the corresponding button:

* UNIX - 8 letters and digits
* PIN - 4 digits

## Ambiguous Letters

This options excludes characters that may look confusing depending on font: 'I', 'l', 'O', '0'.

## Build and Run

JDK-14 is required to build and run the application.

### Build

Make sure Maven toolchain configuration ```toolchain.xml``` contains the following
definition:
```xml
<toolchain>
    <type>jdk</type>
    <provides>
        <version>14</version>
    </provides>
    <configuration>
        <jdkHome>/path/to/jdk-14</jdkHome>
    </configuration>
</toolchain>
```
Execute the following:
```shell script
$ mvn clean package
```

Application JAR and all dependencies will be placed in ```target/jmods```.

### Run

```
mvn javafx:run
```

### Binary Packages

To build binary installers perform the following steps:
* On Microsoft Windows: install [WiX Toolset](https://wixtoolset.org/releases/), add its binary directory to ```PATH``` 
environment variable
* Execute the following commands:
```shell script
$ mvn clean package
$ mvn exec:exec@dist-mac
  or
$ mvn exec:exec@dist-win
```

Installation packages will be found in ```target/dist``` directory.

## Support

There is no support for this application.