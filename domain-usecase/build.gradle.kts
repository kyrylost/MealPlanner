plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            api(projects.domainModel)
            api(projects.commonCore)
            implementation(libs.androidx.paging.common)
            implementation(projects.domainRepository)
        }
    }
}
