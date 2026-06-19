package dev.stukalo.mealplanner.presentation.feature.gateway.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

internal sealed interface ViewIntent : MviIntent {
    data object CheckUserExistence : ViewIntent
}
