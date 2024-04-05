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
            sharedLib {  }
        }
    }
    linuxX64("nativeLinuxX64") {
        binaries {
            sharedLib {  }
        }
    }
    linuxArm64("nativeLinuxArm64") {
        binaries {
            sharedLib {  }
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