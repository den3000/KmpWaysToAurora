plugins {
    kotlin("multiplatform")
    application
}

group = "com.den3000.kmpwaystoaurora.desktop"
version = "1.0-SNAPSHOT"

kotlin {
    jvm { withJava() }
}

application {
    mainClass = "MainKt"
}