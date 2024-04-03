plugins {
    kotlin("multiplatform")
//    id("com.android.library")
}

version = "1.0-SNAPSHOT"


kotlin {
    jvm { withJava() }
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