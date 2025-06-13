rootProject.name = "password-generator"

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("commons", "org.panteleyev:commons:1.6.1")
            library("flatlaf", "com.formdev:flatlaf:3.6")
        }
        create("junit") {
            library("jupiter", "org.junit.jupiter:junit-jupiter:5.13.1")
        }
    }
}