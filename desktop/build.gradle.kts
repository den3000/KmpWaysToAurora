plugins {
    kotlin("multiplatform")
    application
}

group = "com.den3000.kmpwaystoaurora.desktop"
version = "1.0-SNAPSHOT"

kotlin {
    jvm { withJava() }

    mingwX64("nativeWinX64").apply {
        binaries {
            executable("kn-on-aurora-win-x86_64") {
                entryPoint = "main"
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
                entryPoint = "main"
            }
        }
    }

    linuxArm64("nativeLinuxArm64").apply {
        binaries {
            executable("kn-on-aurora-linux-arm64") {
                entryPoint = "main"
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
    }
}

application {
    mainClass = "MainKt"
}