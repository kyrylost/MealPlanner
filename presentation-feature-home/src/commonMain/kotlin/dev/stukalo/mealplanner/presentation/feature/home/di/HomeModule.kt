package dev.stukalo.mealplanner.presentation.feature.home.di

import dev.stukalo.mealplanner.presentation.feature.home.screen.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule =
    module {
        viewModelOf(::HomeViewModel)
    }
