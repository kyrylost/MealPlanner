package dev.stukalo.mealplanner.data.network.fooddatacentral.impl.di

import dev.stukalo.mealplanner.data.network.fooddatacentral.source.FoodDataCentralNetSource
import dev.stukalo.mealplanner.data.network.fooddatacentral.impl.client.foodDataCentralClient
import dev.stukalo.mealplanner.data.network.fooddatacentral.impl.source.FoodDataCentralNetSourceImpl
import org.koin.dsl.module

val foodDataCentralNetworkModule = module {
    single<FoodDataCentralNetSource> {
        FoodDataCentralNetSourceImpl(
            foodDataCentralClient(
                get(),
            ),
        )
    }
}
