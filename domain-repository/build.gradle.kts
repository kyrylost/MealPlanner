plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            api(projects.domainModel)
            implementation(libs.androidx.paging.common)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
