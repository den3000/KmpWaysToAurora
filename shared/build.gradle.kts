plugins {
    kotlin("multiplatform")
    id("com.android.library")
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