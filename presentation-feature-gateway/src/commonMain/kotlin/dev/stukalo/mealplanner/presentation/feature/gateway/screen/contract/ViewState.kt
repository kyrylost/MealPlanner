package dev.stukalo.mealplanner.presentation.feature.gateway.screen.contract

import androidx.compose.runtime.Immutable
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

@Immutable
internal data class ViewState(val isLoading: Boolean = true) : MviViewState
