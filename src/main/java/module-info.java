open module password.generator {
    requires java.base;
    requires javafx.base;
    requires javafx.controls;

    requires kotlin.stdlib.jdk8;

    exports org.panteleyev.passwdgen;
}