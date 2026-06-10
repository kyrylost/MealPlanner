package dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

internal sealed interface ViewIntent : MviIntent {

    data class OnChangeNameInputIntent(
        val value: String,
    ) : ViewIntent
    data class OnChangeDateInputIntent(
        val date: Long?,
    ) : ViewIntent

    data class OnChangeHeightInputIntent(
        val value: String,
    ) : ViewIntent

    data class OnChangeWeightInputIntent(
        val value: String,
    ) : ViewIntent

    data class OnChangeGenderInputIntent(
        val value: String,
    ) : ViewIntent

    data object OnShowDatePickerIntent : ViewIntent

    data object OnHideDatePickerIntent : ViewIntent

    data object OnShowGenderPickerIntent : ViewIntent

    data object OnHideGenderPickerIntent : ViewIntent

    data object OnContinueClickIntent : ViewIntent
}
