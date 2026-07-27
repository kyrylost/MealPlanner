package dev.stukalo.mealplanner.presentation.feature.filters.di

import dev.stukalo.mealplanner.presentation.feature.filters.screen.FiltersViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val filtersModule = module {
    viewModelOf(::FiltersViewModel)
}