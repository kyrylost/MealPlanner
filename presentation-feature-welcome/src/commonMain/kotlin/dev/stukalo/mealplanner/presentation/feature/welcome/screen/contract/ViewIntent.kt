package dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract

import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

internal sealed interface ViewIntent : MviIntent {
    data class OnChangeNameInputIntent(val value: String) : ViewIntent

    data class OnChangeDateInputIntent(val date: Long?) : ViewIntent

    data class OnChangeHeightInputIntent(val value: String) : ViewIntent

    data class OnChangeWeightInputIntent(val value: String) : ViewIntent

    data class OnChangeTargetWeightInputIntent(val value: String) : ViewIntent

    data class OnChangeGenderInputIntent(val value: GenderDomainModel) : ViewIntent

    data class OnChangeActivityLevelInputIntent(val value: ActivityLevelDomainModel) : ViewIntent

    data class OnChangeDietInputIntent(val value: DietDomainModel) : ViewIntent

    data object OnShowDatePickerIntent : ViewIntent

    data object OnHideDatePickerIntent : ViewIntent

    data object OnContinueClickIntent : ViewIntent

    data object OnBackClickIntent : ViewIntent
}
