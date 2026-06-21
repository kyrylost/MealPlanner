import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

configure<LibraryExtension> {
    namespace = "dev.stukalo.mealplanner.data.preferences"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
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
        commonMain.dependencies {
            implementation(libs.datastore)
            implementation(libs.datastore.preferences)
            implementation(libs.datastore.core.okio)
            implementation(libs.okio)
            implementation(libs.kotlinx.coroutines.core)
            implementation(projects.domainModel)
            implementation(projects.domainRepository)
            implementation(libs.koin.core)
        }
        
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}
