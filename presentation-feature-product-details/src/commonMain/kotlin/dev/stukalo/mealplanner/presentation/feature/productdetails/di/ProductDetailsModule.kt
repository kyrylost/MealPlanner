package dev.stukalo.mealplanner.presentation.feature.productdetails.di

import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.ProductDetailsViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productDetailsModule = module {
    viewModel { (productId: String?, barcode: String?) ->
        ProductDetailsViewModel(
            productId = productId,
            barcode = barcode,
            getProductDetailsUseCase = get(),
            logProductConsumedUseCase = get()
        )
    }
}
