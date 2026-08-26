plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm("desktop")

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.dataNetworkCore)
            implementation(projects.dataNetworkOpenfoodfacts)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
        }
        val desktopMain = getByName("desktopMain") {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
    }
}
