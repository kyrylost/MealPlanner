package dev.stukalo.mealplanner.data.network.openfoodfacts.impl.di

import dev.stukalo.mealplanner.data.network.openfoodfacts.impl.client.openFoodFactsClient
import dev.stukalo.mealplanner.data.network.openfoodfacts.impl.source.OpenFoodFactsNetSourceImpl
import dev.stukalo.mealplanner.data.network.openfoodfacts.source.OpenFoodFactsNetSource
import org.koin.dsl.module

val openFoodFactsNetworkModule =
    module {
        single<OpenFoodFactsNetSource> {
            OpenFoodFactsNetSourceImpl(
                openFoodFactsClient(
                    get()
                )
            )
        }
    }
