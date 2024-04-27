plugins {
    kotlin("multiplatform")
}

kotlin {
    js {
        moduleName = getProject().name
        nodejs()
        browser()
        binaries.executable()
    }
}