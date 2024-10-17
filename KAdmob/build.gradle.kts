@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.native.cocoapods)
    id("maven-publish")
    id("signing")
    alias(libs.plugins.maven.publish)
}




apply(plugin = "maven-publish")
apply(plugin = "signing")


tasks.withType<PublishToMavenRepository> {
    val isMac = getCurrentOperatingSystem().isMacOsX
    onlyIf {
        isMac.also {
            if (!isMac) logger.error(
                """
                    Publishing the library requires macOS to be able to generate iOS artifacts.
                    Run the task on a mac or use the project GitHub workflows for publication and release.
                """
            )
        }
    }
}



mavenPublishing {
    coordinates("io.github.the-best-is-best", "kadmob", "1.0.0-rc2")

    publishToMavenCentral(SonatypeHost.S01, true)
    signAllPublications()

    pom {
        name.set("KAdmob")
        description.set("KAdmob is a Kotlin Multiplatform Mobile (KMM) library that enables seamless integration of Google AdMob advertisements in Android and iOS applications using a unified codebase. It provides a simplified interface for displaying banner ads, interstitial ads, and rewarded video ads across both platforms with minimal configuration")
        url.set("https://github.com/the-best-is-best/KMMAdmob")
        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://opensource.org/licenses/Apache-2.0")
            }
        }
        issueManagement {
            system.set("GITHUB")
            url.set("https://github.com/the-best-is-best/KMMAdmob")
        }
        scm {
            connection.set("https://github.com/the-best-is-best/KMMAdmob.git")
            url.set("https://github.com/the-best-is-best/KMMAdmob")
        }
        developers {
            developer {
                id.set("MichelleRaouf")
                name.set("Michelle Raouf")
                email.set("eng.michelle.raouf@gmail.com")

            }
        }
    }

}


signing {
    useGpgCmd()
    sign(publishing.publications)
}


kotlin {
    jvmToolchain(17)
    androidTarget {
        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }


//    jvm()
//
//    js {
//        browser()
//        binaries.executable()
//    }
//
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//        binaries.executable()
//    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "kadmob"
            isStatic = true
        }
    }

    cocoapods {
        version = "1.0"
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"

        // Optional properties
        // Configure the Pod name here instead of changing the Gradle project name
        name = "kadmob"

        framework {
            baseName = "kadmob"
        }
        noPodspec()
        ios.deploymentTarget = "13"

        pod("KAdmob") {
            version = "0.1.0-13-rc"
            extraOpts += listOf("-compiler-option", "-fmodules")

        }
//        pod("Google-Mobile-Ads-SDK"){
//            version =	"11.10.0"
//            extraOpts += listOf("-compiler-option", "-fmodules")
//
//        }


    }
    sourceSets {
        all {
            languageSettings.apply {
                progressiveMode = true
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                if (name.lowercase().contains("ios")) {
                    optIn("kotlinx.cinterop.ExperimentalForeignApi")
                    optIn("kotlinx.cinterop.BetaInteropApi")
                }
            }
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
            implementation(libs.androidx.activityCompose)

            implementation(libs.play.services.ads)

        }



        iosMain.dependencies {
        }

    }
}


android {
    namespace = "org.company.app"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        buildFeatures {
            //enables a Compose tooling support in the AndroidStudio
            compose = true
        }
    }
}

//https://developer.android.com/develop/ui/compose/testing#setup
dependencies {
    androidTestImplementation(libs.androidx.uitest.junit4)
    debugImplementation(libs.androidx.uitest.testManifest)
    //temporary fix: https://youtrack.jetbrains.com/issue/CMP-5864
    androidTestImplementation("androidx.test:monitor") {
        version { strictly("1.6.1") }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ComposeApp"
            packageVersion = "1.0.0"

            linux {
                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
            }
            windows {
                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
            }
            macOS {
                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
                bundleID = "org.company.app.desktopApp"
            }
        }
    }
}