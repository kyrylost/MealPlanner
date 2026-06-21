package dev.stukalo.mealplanner

import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.data.database.di.databaseBuilderModule
import dev.stukalo.mealplanner.data.database.di.databaseModule
import dev.stukalo.mealplanner.data.network.core.di.dataNetworkModule
import dev.stukalo.mealplanner.data.network.edamam.impl.di.edamamNetworkModule
import dev.stukalo.mealplanner.data.network.fooddatacentral.impl.di.foodDataCentralNetworkModule
import dev.stukalo.mealplanner.data.network.openfoodfacts.impl.di.openFoodFactsNetworkModule
import dev.stukalo.mealplanner.data.preferences.di.dataPreferencesModule
import dev.stukalo.mealplanner.data.preferences.di.platformDataPreferencesModule
import dev.stukalo.mealplanner.data.repository.impl.di.dataRepositoryModule
import dev.stukalo.mealplanner.domain.usecase.impl.di.domainModule
import dev.stukalo.mealplanner.presentation.feature.gateway.di.gatewayModule
import dev.stukalo.mealplanner.presentation.feature.host.HostScreen
import dev.stukalo.mealplanner.presentation.feature.host.di.hostModule
import dev.stukalo.mealplanner.presentation.feature.search.di.searchModule
import dev.stukalo.mealplanner.presentation.feature.settings.di.settingsModule
import dev.stukalo.mealplanner.presentation.feature.welcome.di.welcomeModule
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinConfiguration

@Composable
fun App(koinAppDeclaration: KoinAppDeclaration? = null) {
    KoinApplication(
        configuration = koinConfiguration(
            declaration = {
                koinAppDeclaration?.invoke(this)
                modules(
                    databaseBuilderModule,
                    databaseModule,
                    dataNetworkModule,
                    edamamNetworkModule,
                    openFoodFactsNetworkModule,
                    foodDataCentralNetworkModule,
                    dataRepositoryModule,
                    dataPreferencesModule,
                    platformDataPreferencesModule,

                    domainModule,

                    gatewayModule,
                    welcomeModule,
                    searchModule,
                    settingsModule,
                    hostModule,
                )
            }
        ),
        content = {
            HostScreen()
        }
    )
}
