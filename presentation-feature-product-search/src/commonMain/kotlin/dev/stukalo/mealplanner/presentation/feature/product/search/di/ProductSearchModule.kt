package dev.stukalo.mealplanner.presentation.feature.product.search.di

import dev.stukalo.mealplanner.presentation.feature.product.search.screen.ProductSearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val productSearchModule = module {
    viewModelOf(::ProductSearchViewModel)
}
