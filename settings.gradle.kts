rootProject.name = "MealPlanner"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":application")
include(":core-common")
include(":data-database")
include(":data-network-core")
include(":data-network-edamam")
include(":data-network-edamam-impl")
include(":data-network-openfoodfacts")
include(":data-network-openfoodfacts-impl")
include(":data-network-fooddatacentral")
include(":data-network-fooddatacentral-impl")
include(":data-repository-impl")
include(":data-preferences")
include(":data-health")
include(":data-health-impl")

include(":domain-model")
include(":domain-repository")
include(":domain-service")
include(":domain-usecase")
include(":domain-usecase-impl")

include(":presentation-core-styling")
include(":core-localization")
include(":presentation-core-ui")
include(":platform")
include(":presentation-core-navigation")
include(":presentation-feature-host")
include(":presentation-feature-gateway")
include(":presentation-feature-welcome")
include(":presentation-feature-onboarding")
include(":presentation-feature-barcode-scanner")
include(":presentation-feature-main")
include(":presentation-feature-home")
include(":presentation-feature-product-search")
include(":presentation-feature-recipe-search")
include(":presentation-feature-statistics")
include(":presentation-feature-settings")
include(":presentation-feature-recipe-details")
include(":presentation-feature-product-details")
include(":presentation-feature-filters")




