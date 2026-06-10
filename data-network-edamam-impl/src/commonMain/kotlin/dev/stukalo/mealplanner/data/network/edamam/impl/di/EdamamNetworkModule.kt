package dev.stukalo.mealplanner.data.network.edamam.impl.di

import dev.stukalo.mealplanner.data.network.edamam.food.source.EdamamFoodNetSource
import dev.stukalo.mealplanner.data.network.edamam.impl.base.client.edamamBaseClient
import dev.stukalo.mealplanner.data.network.edamam.recipe.source.EdamamRecipeNetSource
import dev.stukalo.mealplanner.data.network.edamam.impl.food.client.edamamFoodClient
import dev.stukalo.mealplanner.data.network.edamam.impl.food.source.EdamamFoodNetSourceImpl
import dev.stukalo.mealplanner.data.network.edamam.impl.recipe.client.edamamRecipeClient
import dev.stukalo.mealplanner.data.network.edamam.impl.recipe.source.EdamamRecipeNetSourceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val edamamNetworkModule = module {
    single(
        qualifier = named(EDAMAM_BASE_CLIENT),
    ) {
        edamamBaseClient(
            get(),
        )
    }

    single<EdamamFoodNetSource> {
        EdamamFoodNetSourceImpl(
             edamamFoodClient(
                 get(
                     qualifier = named(EDAMAM_BASE_CLIENT),
                 ),
             ),
        )
    }

    single<EdamamRecipeNetSource> {
        EdamamRecipeNetSourceImpl(
            edamamRecipeClient(
                get(
                    qualifier = named(EDAMAM_BASE_CLIENT),
                ),
            ),
        )
    }
}

private const val EDAMAM_BASE_CLIENT = "EdamamBaseClient"
