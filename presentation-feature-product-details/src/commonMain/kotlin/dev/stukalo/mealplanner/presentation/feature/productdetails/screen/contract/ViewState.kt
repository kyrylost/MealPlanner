package dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract

import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

data class ViewState(
    val isLoading: Boolean = false,
    val product: ProductDomainModel? = null,
    val error: String? = null,
    val weightInput: String = "100",
    val isDialogVisible: Boolean = false
) : MviViewState
