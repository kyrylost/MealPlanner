package dev.stukalo.mealplanner.presentation.feature.recipe.search.di

import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.RecipeSearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val recipeSearchModule =
    module {
        viewModelOf(::RecipeSearchViewModel)
    }
