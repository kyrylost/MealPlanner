package dev.stukalo.mealplanner.presentation.feature.barcodescanner.di

import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.BarcodeScannerViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val barcodeScannerModule = module {
    viewModelOf(::BarcodeScannerViewModel)
}
