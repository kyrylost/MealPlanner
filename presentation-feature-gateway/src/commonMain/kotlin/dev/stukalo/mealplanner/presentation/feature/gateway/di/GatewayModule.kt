package dev.stukalo.mealplanner.presentation.feature.gateway.di

import dev.stukalo.mealplanner.presentation.feature.gateway.screen.GatewayViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val gatewayModule =
    module {
        viewModelOf(::GatewayViewModel)
    }
