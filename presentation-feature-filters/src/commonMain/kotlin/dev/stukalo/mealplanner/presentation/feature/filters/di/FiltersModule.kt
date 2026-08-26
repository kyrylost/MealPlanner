package dev.stukalo.mealplanner.presentation.feature.filters.di

import dev.stukalo.mealplanner.presentation.feature.filters.screen.FiltersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for the Filters feature.
 */
val filtersModule =
    module {
        viewModel { params ->
            FiltersViewModel(initialFilters = params.getOrNull())
        }
    }
