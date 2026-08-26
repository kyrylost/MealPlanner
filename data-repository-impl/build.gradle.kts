plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm("desktop")

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.coreCommon)
            implementation(projects.domainModel)
            implementation(projects.domainRepository)
            implementation(projects.dataNetworkEdamam)
            implementation(projects.dataNetworkOpenfoodfacts)
            implementation(projects.dataNetworkFooddatacentral)
            implementation(projects.dataDatabase)
            implementation(projects.dataPreferences)
            implementation(projects.dataHealth)
            implementation(libs.androidx.paging.common)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
