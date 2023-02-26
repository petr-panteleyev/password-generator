import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    application
    kotlin("jvm") version "1.8.10"
    id("org.panteleyev.javafxplugin") version "1.0.0"
    id("org.panteleyev.jpackageplugin") version "1.5.1"
}

repositories {
    mavenCentral()
}

version = "23.2.1"

val jvmTarget = 19
val javaFxVersion = "19.0.2.1"
val jUnitVersion = "5.9.1"

javafx {
    modules(javaFxVersion, listOf(
        "javafx.base",
        "javafx.controls"
    ))
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:$jUnitVersion")
}

kotlin {
    jvmToolchain(jvmTarget)
}

val compileJava: JavaCompile by tasks
tasks.compileKotlin {
    destinationDirectory.set(compileJava.destinationDirectory)
    kotlinOptions {
        jvmTarget = "$jvmTarget"
    }
}

application {
    mainModule.set("password.generator")
    mainClass.set("org.panteleyev.passwdgen.PasswordGeneratorApplication")
}

tasks.processResources {
    filesMatching("**/buildInfo.properties") {
        expand(
            mapOf(
                "version" to project.version.toString(),
                "timestamp" to DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now())
            )
        )
    }
}

tasks.test {
    useJUnitPlatform()
}

task("copyDependencies", Copy::class) {
    from(configurations.runtimeClasspath).into("$buildDir/jmods")
}

task("copyJar", Copy::class) {
    from(tasks.jar).into("$buildDir/jmods")
}

tasks.jpackage {
    dependsOn("build", "copyDependencies", "copyJar")

    appName = "Password Generator"
    vendor = "panteleyev.org"
    module = "${application.mainModule.get()}/${application.mainClass.get()}"
    modulePaths = listOf("$buildDir/jmods")
    destination = "$buildDir/dist"

    mac {
        icon = "icons/icons.icns"
    }

    windows {
        icon = "icons/icons.ico"
        winMenu = true
        winDirChooser = true
        winUpgradeUuid = "2e0c0a36-7aab-4d84-a5a1-4d98fa296dad"
        winMenuGroup = "panteleyev.org"
    }

    linux {
        type = org.panteleyev.jpackage.ImageType.APP_IMAGE
        icon = "icons/icon.png"
    }
}
