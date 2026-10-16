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
            library("crypto", "org.panteleyev:org-panteleyev-crypto:1.0.0")
            library("flatlaf", "com.formdev:flatlaf:3.6")
        }
        create("jetbrains") {
            version("kotlin", "2.3.21")
            library("annotations", "org.jetbrains:annotations:26.1.0")
        }
        create("junit") {
            library("jupiter", "org.junit.jupiter:junit-jupiter:6.1.3")
        }
    }
}