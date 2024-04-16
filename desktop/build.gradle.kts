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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }

        val nativeWinX64Main by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }

        val nativeLinuxX64Main by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }

        val nativeLinuxArm64Main by getting  {
            dependencies {
                implementation(project(":shared"))
            }
        }
    }
}

application {
    mainClass = "MainKt"
}