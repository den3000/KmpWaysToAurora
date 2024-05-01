# Tasks

## Run tasks

1. JVM            `gradle :desktop:run`
2. KN Win X64     `gradle :desktop:runKn-on-aurora-win-x86_64DebugExecutableNative`
3. KN Linux X64   `gradle :desktop:runKn-on-aurora-linux-x86_64DebugExecutableNativeLinuxX64`
4. KN Linux Arm64 `gradle :desktop:runKn-on-aurora-linux-arm64DebugExecutableNativeLinuxArm64`
5. JS Browser
   1. Desktop     `gradle :desktop:jsBrowserDevelopmentRun`
   2. Shared      `gradle :shared:jsBrowserDevelopmentRun`
7. Aurora JS      `gradle :shared:jsBuildForAurora`

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

# Curl integration for native targets
While shared module build as static lib to be available on aurora, after adding ktor curl web engine
it will have dynamic dependency on libcurl.so. The good thing, libcurl is available in aurora, the bad
thing - this fact should be explicitly described in .pro file. So, final aurora app should be linked
not only with libshared.a using `-lshared` option, but also using `-lcurl` option.

# SqlDelight DB

1. Generate DB `gradle :shared:generateSqlDelightInterface`
2. On Windows add "__PROJECT_DIR__\libs\winX64\sqlite" to PATH and re-run Android Studio to be able 
to launch executable 
3. To run app in browser - unblock CORS https://chromewebstore.google.com/detail/cors-unblock/lfhmikememgdcahcdlaciloancbhjino?pli=1

## Other problems

1. No native driver for linux arm 64
2. Requires presence of pre-compiled sqlite3, more details 
   1. https://github.com/cashapp/sqldelight/blob/master/drivers/native-driver/build.gradle#L99-L101
   2. https://github.com/cashapp/sqldelight/issues/3033

## SqlDelight JS related references
https://github.com/chrimaeon/curriculumvitae/tree/7566f291cf5463fb0b1e80ad0474cc7e08a6b245
https://stackoverflow.com/a/66768662/6583492
https://kotlinlang.org/docs/running-kotlin-js.html#run-the-browser-target
https://stackoverflow.com/a/67268300/6583492
