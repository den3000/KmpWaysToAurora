plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.9.23"
    id("app.cash.sqldelight") version "2.0.0-alpha05"
}

version = "1.0-SNAPSHOT"

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.den3000.kmpwaystoaurora") // TODO: ???
        }
    }
}

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

    js(IR) {
        moduleName = "shared"
        version = "0.0.1"
        nodejs()
        binaries.library()
    }

    val version_coroutines = "1.8.0"
    val version_sqldelight  = "2.0.0-alpha05"
    val version_ktor = "2.3.10"
    val version_datetime = "0.6.0-RC.2"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$version_coroutines")
                implementation("io.ktor:ktor-client-core:$version_ktor")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$version_ktor")
                implementation("io.ktor:ktor-client-content-negotiation:$version_ktor")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$version_datetime")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$version_ktor")
                implementation("app.cash.sqldelight:android-driver:$version_sqldelight")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$version_ktor")
                implementation("app.cash.sqldelight:sqlite-driver:$version_sqldelight")
            }
        }

        val nativeWinX64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-winhttp:$version_ktor")
                implementation("app.cash.sqldelight:native-driver:$version_sqldelight")
            }
        }

        val nativeLinuxX64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-curl:$version_ktor")
                implementation("io.ktor:ktor-client-cio:$version_ktor")
                implementation("app.cash.sqldelight:native-driver:$version_sqldelight")
            }
        }

        val nativeLinuxArm64Main by getting  {
            dependencies {
                // Not yet available
                // implementation("io.ktor:ktor-client-curl:$ktor_version")

                // Not yet available
                // implementation("app.cash.sqldelight:native-driver:$version_sqldelight")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$version_ktor")
                implementation("app.cash.sqldelight:sqljs-driver:$version_sqldelight")
                implementation(npm("sql.js", "1.8.0")) // 1.8.0 is SUPER IMPORTANT
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
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