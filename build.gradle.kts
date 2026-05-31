// Copyright © 2025-2026 Petr Panteleyev
// SPDX-License-Identifier: BSD-2-Clause
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.panteleyev.jpackage.ImageType

plugins {
    kotlin("jvm") version jetbrains.versions.kotlin
    application
    id("org.panteleyev.jlinkplugin") version "2.0.0"
    id("org.panteleyev.jpackageplugin") version "2.0.1"
}

version = "26.5.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.commons)
    implementation(libs.flatlaf)

    implementation(jetbrains.annotations)

    testImplementation(junit.jupiter)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

kotlin {
    jvmToolchain(25)
}

tasks.compileKotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_25)
    }
}

application {
    mainModule.set("password.generator")
    mainClass.set("org.panteleyev.passwdgen.PasswordGeneratorApplicationKt")
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
