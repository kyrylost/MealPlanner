plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm("desktop")

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.domainUsecase)
            implementation(projects.domainRepository)
            implementation(libs.androidx.paging.common)
            implementation(libs.koin.core)
        }
    }
}
