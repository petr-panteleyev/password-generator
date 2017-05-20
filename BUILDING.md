# Building Password Generator

## Standalone JAR

In order to build standalone JAR perform the following steps:

```
git clone https://github.com/petr-panteleyev/password-generator.git
cd password-generator
mvn clean
mvn package
```

## Native Packages

In order to build native package perform the following steps:

```
cd password-generator
mvn clean
mvn package -P fatjar
mvn exec:exec@<native-dist>
```

Where &lt;native-dist> depends on native OS and packaging.

`dist-mac` produces DMG file. Its content can be copied to the Applications folder as is.

`dist-win` produces EXE file with a simple installer. This option requires additional software. Please refer to
[javapackager](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javapackager.html) documentation for details.

`dist-rpm` produces RPM file. This option was tested on OpenSUSE Leap 42.2.

Resulting package can be found in `target/dists/bundles`.
