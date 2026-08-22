package dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent
import org.jetbrains.compose.resources.StringResource

/**
 * Represents single events emitted by the Statistics screen (e.g., navigation, toast).
 */
internal sealed interface ViewEvent : MviSingleEvent {
    data class ShowError(val message: StringResource) : ViewEvent
}
