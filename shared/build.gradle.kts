plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.9.23"
}

version = "1.0-SNAPSHOT"

kotlin {
    androidTarget()
    jvm { }
    mingwX64("nativeWinX64") {
        binaries {
            staticLib {  }
        }
    }
    linuxX64("nativeLinuxX64") {
        binaries {
            staticLib {  }
        }
    }
    linuxArm64("nativeLinuxArm64") {
        binaries {
            staticLib {  }
        }
    }

    val coroutines_version = "1.8.0"
    val ktor_version = "2.3.10"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                implementation("io.ktor:ktor-client-core:$ktor_version")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktor_version")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutines_version")
                implementation("io.ktor:ktor-client-okhttp:$ktor_version")
            }
        }

        val nativeWinX64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-winhttp:$ktor_version")
            }
        }

        val nativeLinuxX64Main by getting {
            dependencies {
                // TODO: Add curl engine
                implementation("io.ktor:ktor-client-cio:$ktor_version")
            }
        }

        val nativeLinuxArm64Main by getting  {
            dependencies {
                // TODO: Add curl engine
                implementation("io.ktor:ktor-client-cio:$ktor_version")
            }
        }
    }
}

android {
    compileSdk = 34
    namespace = "com.den3000.kmpwaystoaurora.shared"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

//region tasks
val tnLinkDebugLinuxX64 = "linkDebugStaticNativeLinuxX64"
val tnLinkDebugLinuxArm64 = "linkDebugStaticNativeLinuxArm64"
val tnLinkReleaseLinuxX64 = "linkReleaseStaticNativeLinuxX64"
val tnLinkReleaseLinuxArm64 = "linkReleaseStaticNativeLinuxArm64"
val tnCopAndLinkDebugLinuxX64 = "linkAndCopyDebugStaticNativeLinuxX64"
val tnCopAndLinkDebugLinuxArm64 = "linkAndCopyDebugStaticNativeLinuxArm64"
val tnCopAndLinkReleaseLinuxX64 = "linkAndCopyReleaseStaticNativeLinuxX64"
val tnCopAndLinkReleaseLinuxArm64 = "linkAndCopyReleaseStaticNativeLinuxArm64"

fun Copy.copy(srcTarget:String, srcBuild: String, dstTarget: String, dstBuild: String) {
    from(
        layout.buildDirectory.file("bin/$srcTarget/$srcBuild/libshared.a"),
        layout.buildDirectory.file("bin/$srcTarget/$srcBuild/libshared_api.h")
    )
    into(layout.projectDirectory.dir("../aurora/KmpWaysToAurora/lib_shared/$dstTarget/$dstBuild/"))
}

tasks.register<Copy>(tnCopAndLinkDebugLinuxX64) {
    dependsOn(tasks.getByName(tnLinkDebugLinuxX64))
    copy("nativeLinuxX64", "debugStatic", "x86_64", "debug")
}

tasks.register<Copy>(tnCopAndLinkDebugLinuxArm64) {
    dependsOn(tasks.getByName(tnLinkDebugLinuxArm64))
    copy("nativeLinuxArm64", "debugStatic", "aarch64", "debug")
}

tasks.register<Copy>(tnCopAndLinkReleaseLinuxX64) {
    dependsOn(tasks.getByName(tnLinkReleaseLinuxX64))
    copy("nativeLinuxX64", "releaseStatic", "x86_64", "release")
}

tasks.register<Copy>(tnCopAndLinkReleaseLinuxArm64) {
    dependsOn(tasks.getByName(tnLinkReleaseLinuxArm64))
    copy("nativeLinuxArm64", "releaseStatic", "aarch64", "release")
}

tasks.register("linkAndCopySharedForAllTargets") {
    dependsOn(
        tasks.getByName(tnCopAndLinkDebugLinuxX64),
        tasks.getByName(tnCopAndLinkDebugLinuxArm64),
        tasks.getByName(tnCopAndLinkReleaseLinuxX64),
        tasks.getByName(tnCopAndLinkReleaseLinuxArm64)
    )
}
//endregion