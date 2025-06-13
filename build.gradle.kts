/*
 Copyright © 2025 Petr Panteleyev <petr@panteleyev.org>
 SPDX-License-Identifier: BSD-2-Clause
 */
import org.panteleyev.jpackage.ImageType

plugins {
    application
    id("org.panteleyev.jlinkplugin") version "1.0.0"
    id("org.panteleyev.jpackageplugin") version "1.7.1"
}

version = "25.6.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.commons)
    implementation(libs.flatlaf)

    testImplementation(junit.jupiter)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}

application {
    mainModule.set("password.generator")
    mainClass.set("org.panteleyev.passwdgen.PasswordGeneratorApplication")
}

tasks.register("copyDependencies", Copy::class) {
    from(configurations.runtimeClasspath).into(layout.buildDirectory.dir("jmods"))
}

tasks.register("copyJar", Copy::class) {
    from(tasks.jar).into(layout.buildDirectory.dir("jmods"))
}

tasks.jlink {
    dependsOn("build", "copyDependencies", "copyJar")

    modulePaths.setFrom(tasks.named("copyJar"))
    addModules = listOf("ALL-MODULE-PATH")

    noHeaderFiles = true
    noManPages = true
    stripDebug = true
    generateCdsArchive = true

    output.set(layout.buildDirectory.dir("jlink"))
}

tasks.jpackage {
    appName = "Password Generator"
    appVersion = project.version.toString()
    vendor = "panteleyev.org"
    module = "${application.mainModule.get()}/${application.mainClass.get()}"
    runtimeImage = tasks.jlink.get().output
    destination = layout.buildDirectory.dir("dist")

    windows {
        icon = layout.projectDirectory.file("icons/icons.ico")
        winMenu = true
        winDirChooser = true
        winUpgradeUuid = "2e0c0a36-7aab-4d84-a5a1-4d98fa296dad"
        winMenuGroup = "panteleyev.org"
    }

    mac {
        icon = layout.projectDirectory.file("icons/icons.icns")
    }

    linux {
        type = ImageType.APP_IMAGE
        icon = layout.projectDirectory.file("icons/icon.png")
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
