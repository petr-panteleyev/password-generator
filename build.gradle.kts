import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    application
    kotlin("jvm") version "1.5.0"
    id("org.openjfx.javafxplugin") version "0.0.10"
    id("org.panteleyev.jpackageplugin") version "1.3.0"
}

repositories {
    mavenCentral()
}

version = "21.3.0"

val testNgVersion = "7.3.0"

javafx {
    version = "16"
    modules(
        "javafx.base",
        "javafx.controls"
    )
}

dependencies {
    testImplementation("org.testng:testng:$testNgVersion")
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "16"
    }
}

application {
    mainModule.set("password.generator")
    mainClass.set("org.panteleyev.passwdgen.PasswordGeneratorApplication")
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8")
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
    useTestNG()
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
    javaOptions = listOf("-Dfile.encoding=UTF-8")

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
}
