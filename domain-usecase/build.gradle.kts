plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm("desktop")

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(projects.domainModel)
            api(projects.commonCore)
            implementation(libs.androidx.paging.common)
            implementation(libs.kotlinx.datetime)
            implementation(projects.domainRepository)
        }
    }
}
