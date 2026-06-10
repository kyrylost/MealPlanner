package dev.stukalo.mealplanner.presentation.feature.search.di

import dev.stukalo.mealplanner.presentation.feature.search.screen.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchModule = module {
    viewModelOf(::SearchViewModel)
}
