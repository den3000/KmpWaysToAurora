plugins {
    kotlin("multiplatform")
}

kotlin {
    js(IR) {
        moduleName = getProject().name
        nodejs()
        binaries.executable()
    }

    val version_coroutines = "1.8.0"
    val version_datetime = "0.6.0-RC.2"

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$version_coroutines")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$version_datetime")

                implementation(project(":shared"))
            }
        }
    }
}