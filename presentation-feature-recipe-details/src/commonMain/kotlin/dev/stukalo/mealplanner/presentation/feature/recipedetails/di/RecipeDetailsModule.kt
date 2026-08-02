package dev.stukalo.mealplanner.presentation.feature.recipedetails.di

import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.RecipeDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val recipeDetailsModule =
    module {
        viewModelOf(::RecipeDetailsViewModel)
    }
