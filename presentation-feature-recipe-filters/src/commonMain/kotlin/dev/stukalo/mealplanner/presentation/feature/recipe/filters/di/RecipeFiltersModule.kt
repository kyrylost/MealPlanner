package dev.stukalo.mealplanner.presentation.feature.recipe.filters.di

import dev.stukalo.mealplanner.presentation.feature.recipe.filters.screen.RecipeFiltersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for the Recipe Filters feature.
 */
val recipeFiltersModule =
    module {
        viewModel { params ->
            RecipeFiltersViewModel(initialFilters = params.getOrNull())
        }
    }
