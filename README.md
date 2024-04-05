# Run Tasks

1. JVM `gradle :desktop:run`
2. KN Win X64 `gradle :desktop:runKn-on-aurora-win-x86_64DebugExecutableNative`
3. KN Linux X64 `gradle :desktop:runKn-on-aurora-linux-x86_64DebugExecutableNativeLinuxX64`
4. KN Linux Arm64 `gradle :desktop:runKn-on-aurora-linux-arm64DebugExecutableNativeLinuxArm64`

## Build tasks for executables

1. KN Win X64 `gradle :desktop:linkKn-on-aurora-win-x86_64DebugExecutableNative`
2. KN Linux X64 `gradle :desktop:linkKn-on-aurora-linux-x86_64DebugExecutableNativeLinuxX64`
3. KN Linux Arm64 `gradle :desktop:linkKn-on-aurora-linux-arm64DebugExecutableNativeLinuxArm64`

## Build tasks for static libs

1. KN Win X64 `gradle :shared:linkReleaseStaticNativeWinX64`
2. KN Linux X64 `gradle :shared:linkReleaseStaticNativeLinuxX64`
3. KN Linux Arm64 `gradle :shared:linkReleaseStaticNativeLinuxArm64`

[//]: # (
    TODO: 
        Add custom gradle task that will generate all necessary static libs from kotlin native 
        and properly copy them to aurora project
)