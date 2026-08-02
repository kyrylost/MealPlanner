package dev.stukalo.mealplanner.presentation.feature.host.di

import dev.stukalo.mealplanner.presentation.feature.host.HostViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val hostModule =
    module {
        viewModelOf(::HostViewModel)
    }
