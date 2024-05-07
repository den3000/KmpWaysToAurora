import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    application

    id("org.graalvm.buildtools.native") version "0.10.1"
    id("com.google.osdetector") version "1.7.3"
}

val isWindows: Boolean by extra { osdetector.os == "windows" }

group = "com.den3000.kmpwaystoaurora.desktop"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

kotlin {
    jvm { withJava() }

    mingwX64("nativeWinX64").apply {
        binaries {
            executable("kn-on-aurora-win-x86_64") {
                entryPoint = "com.den3000.kmpwaystoaurora.desktop.main"
                linkerOpts += listOf(
                    "-L$projectDir/../libs/winX64/sqlite",
                    "-lsqlite3",
                )
            }
        }
    }

    linuxX64("nativeLinuxX64").apply {
        binaries {
            executable("kn-on-aurora-linux-x86_64") {
                entryPoint = "com.den3000.kmpwaystoaurora.desktop.main"
            }
        }
    }

    linuxArm64("nativeLinuxArm64").apply {
        binaries {
            executable("kn-on-aurora-linux-arm64") {
                entryPoint = "com.den3000.kmpwaystoaurora.desktop.main"
            }
        }
    }

    js(IR) {
        moduleName = getProject().name
        browser()
        binaries.executable()
    }

    val version_coroutines = "1.8.0"
    val version_datetime = "0.6.0-RC.2"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$version_coroutines")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$version_datetime")
                implementation(project(":shared"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("org.graalvm.sdk:graal-sdk:24.0.1")
            }
        }
    }
}

application {
    mainClass = "com.den3000.kmpwaystoaurora.desktop.MainKt"
}

graalvmNative {
    toolchainDetection.set(false)
    binaries{
        named("main") {
//            mainClass.set("com.den3000.kmpwaystoaurora.desktop.MainKt")
//            buildArgs(
//                "-Djava.awt.headless=false",
//                // build as static executable
//                "-H:+StaticExecutableWithDynamicLibC",
//            )

            mainClass.set("com.den3000.kmpwaystoaurora.desktop.Jtn")
            buildArgs(
                // build as lib, not executable
                "--shared",
            )
        }
    }

    agent{
        defaultMode.set("standard")

        metadataCopy {
            inputTaskNames.add("run") // Tasks previously executed with the agent attached.
            outputDirectories.add("src/main/resources/META-INF/native-image")
            mergeWithExisting.set(true)
        }
    }
}