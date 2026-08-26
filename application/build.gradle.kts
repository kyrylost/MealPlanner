import com.android.build.api.dsl.ApplicationExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "MainApplication"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.core.splashscreen)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.adaptive)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(projects.coreCommon)
            implementation(projects.coreLocalization)
            implementation(projects.dataDatabase)
            implementation(projects.dataNetworkCore)
            implementation(projects.dataNetworkEdamam)
            implementation(projects.dataNetworkEdamamImpl)
            implementation(projects.dataNetworkOpenfoodfacts)
            implementation(projects.dataNetworkOpenfoodfactsImpl)
            implementation(projects.dataNetworkFooddatacentral)
            implementation(projects.dataNetworkFooddatacentralImpl)
            implementation(projects.dataRepositoryImpl)
            implementation(projects.dataPreferences)
            implementation(projects.dataHealth)
            implementation(projects.dataHealthImpl)
            implementation(projects.platform)
            implementation(projects.domainModel)
            implementation(projects.domainRepository)
            implementation(projects.domainUsecase)
            implementation(projects.domainUsecaseImpl)
            implementation(projects.presentationCoreStyling)
            implementation(projects.presentationCoreUi)
            implementation(projects.presentationFeatureHost)
            implementation(projects.presentationFeatureGateway)
            implementation(projects.presentationFeatureOnboarding)
            implementation(projects.presentationFeatureWelcome)
            implementation(projects.presentationFeatureHome)
            implementation(projects.presentationFeatureStatistics)
            implementation(projects.presentationFeatureProductSearch)
            implementation(projects.presentationFeatureSettings)
            implementation(projects.presentationFeatureBarcodeScanner)
            implementation(projects.presentationFeatureRecipeSearch)
            implementation(projects.presentationFeatureRecipeFilters)
            implementation(projects.presentationFeatureRecipeDetails)
            implementation(projects.presentationFeatureProductDetails)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

configure<ApplicationExtension> {
    val signingProps = Properties().apply {
        val propFile = rootProject.file("configure/signing.properties")
        if (propFile.exists()) {
            load(propFile.inputStream())
        }
    }

    namespace = "dev.stukalo.mealplanner"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    defaultConfig {
        applicationId = "dev.stukalo.mealplanner"
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.android.targetSdk
                .get()
                .toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            val keystorePath = signingProps.getProperty("RELEASE_STORE_FILE")
            if (!keystorePath.isNullOrBlank()) {
                signingConfig = signingConfigs.create("release") {
                    storeFile = rootProject.file(keystorePath)
                    storePassword = signingProps.getProperty("RELEASE_STORE_PASSWORD")
                    keyAlias = signingProps.getProperty("RELEASE_KEY_ALIAS")
                    keyPassword = signingProps.getProperty("RELEASE_KEY_PASSWORD")
                }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    lint {
        disable += "NullSafeMutableLiveData"
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}

compose.desktop {
    application {
        mainClass = "MainKt"
        // For development
        jvmArgs += "-splash:${project.projectDir}/src/desktopMain/resources/splash.png"
        // For packaged app
        jvmArgs += $$"-splash:$APPDIR/resources/splash.png"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.stukalo.mealplanner"
            packageVersion = "1.0.0"
            appResourcesRootDir = rootDir.resolve("myAssets")

            val iconsRoot = project.file("desktop-icons")
            windows {
                iconFile.set(iconsRoot.resolve("icon-windows.ico"))
            }
            macOS {
                iconFile.set(iconsRoot.resolve("icon-mac.icns"))
            }
            linux {
                iconFile.set(iconsRoot.resolve("icon-linux.png"))
            }
        }
    }
}

tasks.withType<org.gradle.jvm.tasks.Jar> {
    manifest {
        attributes["SplashScreen-Image"] = "splash.png"
    }
}
