package dev.stukalo.mealplanner

import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.core.platform.di.platformModule
import dev.stukalo.mealplanner.data.database.di.databaseBuilderModule
import dev.stukalo.mealplanner.data.database.di.databaseModule
import dev.stukalo.mealplanner.data.health.impl.di.healthDataModule
import dev.stukalo.mealplanner.data.network.core.di.dataNetworkModule
import dev.stukalo.mealplanner.data.network.edamam.impl.di.edamamNetworkModule
import dev.stukalo.mealplanner.data.network.fooddatacentral.impl.di.foodDataCentralNetworkModule
import dev.stukalo.mealplanner.data.network.openfoodfacts.impl.di.openFoodFactsNetworkModule
import dev.stukalo.mealplanner.data.preferences.di.dataPreferencesModule
import dev.stukalo.mealplanner.data.preferences.di.platformDataPreferencesModule
import dev.stukalo.mealplanner.data.repository.impl.di.dataRepositoryModule
import dev.stukalo.mealplanner.domain.usecase.impl.di.domainModule
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.di.barcodeScannerModule
import dev.stukalo.mealplanner.presentation.feature.filters.di.filtersModule
import dev.stukalo.mealplanner.presentation.feature.gateway.di.gatewayModule
import dev.stukalo.mealplanner.presentation.feature.home.di.homeModule
import dev.stukalo.mealplanner.presentation.feature.host.HostScreen
import dev.stukalo.mealplanner.presentation.feature.host.di.hostModule
import dev.stukalo.mealplanner.presentation.feature.onboarding.di.onboardingModule
import dev.stukalo.mealplanner.presentation.feature.product.search.di.productSearchModule
import dev.stukalo.mealplanner.presentation.feature.productdetails.di.productDetailsModule
import dev.stukalo.mealplanner.presentation.feature.recipe.search.di.recipeSearchModule
import dev.stukalo.mealplanner.presentation.feature.recipedetails.di.recipeDetailsModule
import dev.stukalo.mealplanner.presentation.feature.settings.di.settingsModule
import dev.stukalo.mealplanner.presentation.feature.statistics.di.statisticsModule
import dev.stukalo.mealplanner.presentation.feature.welcome.di.welcomeModule
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinConfiguration

@Composable
fun App(koinAppDeclaration: KoinAppDeclaration? = null) {
    KoinApplication(
        configuration =
        koinConfiguration(
            declaration = {
                koinAppDeclaration?.invoke(this)
                modules(
                    databaseBuilderModule,
                    databaseModule,
                    dataNetworkModule,
                    healthDataModule,
                    edamamNetworkModule,
                    openFoodFactsNetworkModule,
                    foodDataCentralNetworkModule,
                    dataRepositoryModule,
                    dataPreferencesModule,
                    platformDataPreferencesModule,
                    platformModule,
                    domainModule,
                    gatewayModule,
                    onboardingModule,
                    homeModule,
                    welcomeModule,
                    barcodeScannerModule,
                    recipeSearchModule,
                    productSearchModule,
                    settingsModule,
                    statisticsModule,
                    filtersModule,
                    recipeDetailsModule,
                    productDetailsModule,
                    hostModule
                )
            }
        ),
        content = {
            HostScreen()
        }
    )
}
