import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":common-core"))
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
        }
        val desktopMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
    }
}

// Read the local.properties file
val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.inputStream())
}

abstract class GenerateSecretsTask : DefaultTask() {
    @get:Input
    abstract val edamamFoodApiAppId: Property<String>

    @get:Input
    abstract val edamamFoodApiAppKey: Property<String>

    @get:Input
    abstract val edamamRecipeApiAppId: Property<String>

    @get:Input
    abstract val edamamRecipeApiAppKey: Property<String>

    @get:Input
    abstract val usdaFdcApiKey: Property<String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun generate() {
        val secretsFile = outputDirectory.get().file("ApiKeys.kt").asFile
        secretsFile.parentFile.mkdirs()
        secretsFile.writeText("""
            package dev.stukalo.mealplanner.data.network.core
            
            // This file is auto-generated. Do not edit.
            object ApiKeys {
                const val EDAMAM_FOOD_API_APP_ID = "${edamamFoodApiAppId.get()}"
                const val EDAMAM_FOOD_API_APP_KEY = "${edamamFoodApiAppKey.get()}"
                const val EDAMAM_RECIPE_API_APP_ID = "${edamamRecipeApiAppId.get()}"
                const val EDAMAM_RECIPE_API_APP_KEY = "${edamamRecipeApiAppKey.get()}"
                const val USDA_FDC_API_KEY = "${usdaFdcApiKey.get()}"
            }
        """.trimIndent())
    }
}

// Create a task to generate a Kotlin file
val generateSecrets = tasks.register<GenerateSecretsTask>("generateSecrets") {
    edamamFoodApiAppId.set(localProperties.getProperty("EDAMAM_FOOD_API_APP_ID") ?: "MISSING_KEY")
    edamamFoodApiAppKey.set(localProperties.getProperty("EDAMAM_FOOD_API_APP_KEY") ?: "MISSING_KEY")
    edamamRecipeApiAppId.set(localProperties.getProperty("EDAMAM_RECIPE_API_APP_ID") ?: "MISSING_KEY")
    edamamRecipeApiAppKey.set(localProperties.getProperty("EDAMAM_RECIPE_API_APP_KEY") ?: "MISSING_KEY")
    usdaFdcApiKey.set(localProperties.getProperty("USDA_FDC_API_KEY") ?: "MISSING_KEY")
    outputDirectory.set(layout.buildDirectory.dir("generated/secrets/src/commonMain/kotlin"))
}

// Tell Kotlin to include this generated folder in commonMain
kotlin {
    sourceSets {
        commonMain {
            kotlin.srcDir(generateSecrets)
        }
    }
}
