// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // app
    id("com.android.application") version "8.2.0-rc03" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false

    // desktop, shared, jsApp
    kotlin("multiplatform") version "1.9.20" apply false
}