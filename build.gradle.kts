// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // app
    id("com.android.application") version "8.2.0-rc03" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

    // desktop
    kotlin("jvm") version "1.9.23" apply false
}