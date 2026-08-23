package dev.stukalo.mealplanner.presentation.feature.productdetails.di

import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.ProductDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val productDetailsModule = module {
    viewModelOf(::ProductDetailsViewModel)
}
