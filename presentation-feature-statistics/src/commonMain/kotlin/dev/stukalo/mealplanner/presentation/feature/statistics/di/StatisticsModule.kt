package dev.stukalo.mealplanner.presentation.feature.statistics.di

import dev.stukalo.mealplanner.presentation.feature.statistics.screen.StatisticsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val statisticsModule =
    module {
        viewModelOf(::StatisticsViewModel)
    }
