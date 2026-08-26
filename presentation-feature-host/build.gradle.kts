import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
}

configure<LibraryExtension> {
    namespace = "dev.stukalo.mealplanner.presentation.feature.host"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm("desktop")

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val desktopMain = getByName("desktopMain")

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)
            implementation(libs.material)
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
            implementation(libs.navigation.compose)
            implementation(projects.presentationCoreNavigation)
            implementation(projects.presentationCoreStyling)
            implementation(projects.presentationCoreUi)
            implementation(projects.presentationFeatureMain)
            implementation(projects.presentationFeatureGateway)
            implementation(projects.presentationFeatureWelcome)
            implementation(projects.presentationFeatureOnboarding)
            implementation(projects.presentationFeatureBarcodeScanner)
            implementation(projects.presentationFeatureRecipeDetails)
            implementation(projects.presentationFeatureSettings)
            implementation(projects.presentationFeatureRecipeFilters)
            implementation(projects.presentationFeatureRecipeSearch)
            implementation(projects.presentationFeatureProductDetails)
            implementation(projects.domainModel)
            implementation(projects.domainUsecase)
            implementation(libs.koin.compose.viewmodel)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}
