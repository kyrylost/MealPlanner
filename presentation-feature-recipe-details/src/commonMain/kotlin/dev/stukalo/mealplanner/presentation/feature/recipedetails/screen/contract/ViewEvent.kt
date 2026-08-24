package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent
import org.jetbrains.compose.resources.StringResource

internal sealed interface ViewEvent : MviSingleEvent {
    data object NavigateBack : ViewEvent
    data class ShowError(val message: StringResource) : ViewEvent
}
