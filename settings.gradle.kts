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
        create("jetbrains") {
            version("kotlin", "2.3.21")
            library("annotations", "org.jetbrains:annotations:26.1.0")
        }
        create("junit") {
            library("jupiter", "org.junit.jupiter:junit-jupiter:6.1.0")
        }
    }
}