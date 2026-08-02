package dev.stukalo.mealplanner.presentation.feature.welcome.di

import dev.stukalo.mealplanner.presentation.feature.welcome.screen.WelcomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val welcomeModule =
    module {
        viewModelOf(::WelcomeViewModel)
    }
