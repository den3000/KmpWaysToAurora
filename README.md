# Run Tasks

1. JVM `gradle :desktop:run`
2. KN Win X64 `gradle :desktop:runKn-on-aurora-win-x86_64DebugExecutableNative`
3. KN Linux X64 `gradle :desktop:runKn-on-aurora-linux-x86_64DebugExecutableNativeLinuxX64`
4. KN Linux Arm64 `gradle :desktop:runKn-on-aurora-linux-arm64DebugExecutableNativeLinuxArm64`
5. JS `gradle :jsApp:jsNodeDevelopmentRun`
6. JS `gradle :jsApp:jsBrowserDevelopmentRun`

## Build tasks for executables

1. KN Win X64 `gradle :desktop:linkKn-on-aurora-win-x86_64DebugExecutableNative`
2. KN Linux X64 `gradle :desktop:linkKn-on-aurora-linux-x86_64DebugExecutableNativeLinuxX64`
3. KN Linux Arm64 `gradle :desktop:linkKn-on-aurora-linux-arm64DebugExecutableNativeLinuxArm64`

## Build tasks for static libs release

1. KN Win X64 `gradle :shared:linkReleaseStaticNativeWinX64`
2. KN Linux X64 `gradle :shared:linkReleaseStaticNativeLinuxX64`
3. KN Linux Arm64 `gradle :shared:linkReleaseStaticNativeLinuxArm64`

## Build tasks for static libs debug

1. KN Win X64 `gradle :shared:linkDebugStaticNativeWinX64`
2. KN Linux X64 `gradle :shared:linkDebugStaticNativeLinuxX64`
3. KN Linux Arm64 `gradle :shared:linkDebugStaticNativeLinuxArm64`

## Managing lib_shared build

1. `gradle :shared:linkAndCopyDebugStaticNativeLinuxX64` 
2. `gradle :shared:linkAndCopyDebugStaticNativeLinuxArm64` 
3. `gradle :shared:linkAndCopyReleaseStaticNativeLinuxX64`
4. `gradle :shared:linkAndCopyReleaseStaticNativeLinuxArm64` 
5. `gradle :shared:linkAndCopySharedForAllTargets`

## SqlDelight DB

1. task to generate db `gradle :shared:generateSqlDelightInterface`
2. Add "__PROJECT_DIR__\libs\winX64\sqlite" to PATH to be able to launch executable and re-run Android Studio
3. no native driver for linux arm 64
4. Requires presence of pre-compiled sqlite3, more details 
   1. https://github.com/cashapp/sqldelight/blob/master/drivers/native-driver/build.gradle#L99-L101
   2. https://github.com/cashapp/sqldelight/issues/3033

# Curl integration
While shared module build as static lib to be available on aurora, after adding ktor curl web engine
it will have dynamic dependency on libcurl.so. The good thing, libcurl is available in aurora, the bad
thing - this fact should be explicitly described in .pro file. So, final aurora app should be linked 
not only with libshared.a using `-lshared` option, but also using `-lcurl` option.

# JS
https://github.com/chrimaeon/curriculumvitae/tree/7566f291cf5463fb0b1e80ad0474cc7e08a6b245
https://stackoverflow.com/a/66768662/6583492
https://kotlinlang.org/docs/running-kotlin-js.html#run-the-browser-target
https://stackoverflow.com/a/67268300/6583492