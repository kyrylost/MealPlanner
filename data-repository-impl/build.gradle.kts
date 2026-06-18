plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm("desktop")

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.commonCore)
            implementation(projects.domainModel)
            implementation(projects.domainRepository)
            implementation(projects.dataNetworkEdamam)
            implementation(projects.dataNetworkOpenfoodfacts)
            implementation(projects.dataNetworkFooddatacentral)
            implementation(projects.dataDatabase)
            implementation(libs.androidx.paging.common)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
        }
    }
}
