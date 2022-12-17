plugins {
    kotlin("multiplatform") version "1.7.21"
}

repositories {
    mavenCentral()
}

kotlin {


    val libName = "KtCalculator"  /*Replace with you name */

    ios {
        binaries.framework(libName)
    }

    tasks {

        register("buildDebugXCFramework", Exec::class.java) {
            description = "Create a Debug XCFramework"

            dependsOn("link${libName}DebugFrameworkIosArm64")
            dependsOn("link${libName}DebugFrameworkIosX64")

            val arm64FrameworkPath =
                "$rootDir/build/bin/iosArm64/${libName}DebugFramework/${libName}.framework"
            val arm64DebugSymbolsPath =
                "$rootDir/build/bin/iosArm64/${libName}DebugFramework/${libName}.framework.dSYM"

            val x64FrameworkPath =
                "$rootDir/build/bin/iosX64/${libName}DebugFramework/${libName}.framework"
            val x64DebugSymbolsPath =
                "$rootDir/build/bin/iosX64/${libName}DebugFramework/${libName}.framework.dSYM"

            val xcFrameworkDest = File("$rootDir/build/xcframework/debug/$libName.xcframework")

            executable = "xcodebuild"
            args(mutableListOf<String>().apply {
                add("-create-xcframework")
                add("-output")
                add(xcFrameworkDest.path)

                // Real Device
                add("-framework")
                add(arm64FrameworkPath)
                add("-debug-symbols")
                add(arm64DebugSymbolsPath)

                // Simulator
                add("-framework")
                add(x64FrameworkPath)
                add("-debug-symbols")
                add(x64DebugSymbolsPath)
            })

            doFirst {
                xcFrameworkDest.deleteRecursively()
            }
        }


        register("buildReleaseXCFramework", Exec::class.java) {
            description = "Create a Release XCFramework"

            dependsOn("link${libName}ReleaseFrameworkIosArm64")
            dependsOn("link${libName}ReleaseFrameworkIosX64")


            val arm64FrameworkPath =
                "$rootDir/build/bin/iosArm64/${libName}ReleaseFramework/${libName}.framework"
            val arm64DebugSymbolsPath =
                "$rootDir/build/bin/iosArm64/${libName}ReleaseFramework/${libName}.framework.dSYM"

            val x64FrameworkPath =
                "$rootDir/build/bin/iosX64/${libName}ReleaseFramework/${libName}.framework"
            val x64DebugSymbolsPath =
                "$rootDir/build/bin/iosX64/${libName}ReleaseFramework/${libName}.framework.dSYM"

            val xcFrameworkDest = File("$rootDir/build/xcframework/release/$libName.xcframework")
            executable = "xcodebuild"
            args(mutableListOf<String>().apply {
                add("-create-xcframework")
                add("-output")
                add(xcFrameworkDest.path)

                // Real Device
                add("-framework")
                add(arm64FrameworkPath)
                add("-debug-symbols")
                add(arm64DebugSymbolsPath)

                // Simulator
                add("-framework")
                add(x64FrameworkPath)
                add("-debug-symbols")
                add(x64DebugSymbolsPath)
            })

            doFirst {
                xcFrameworkDest.deleteRecursively()
            }
        }


    }

}

tasks.wrapper {
    gradleVersion = "6.7.1"
    distributionType = Wrapper.DistributionType.ALL
}