package dev.stukalo.mealplanner.presentation.feature.settings.screen.contract

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

sealed interface ViewIntent : MviIntent {
    data class OnThemeClick(val palette: ColorPaletteDomainModel) : ViewIntent
    data class OnLanguageClick(val language: String) : ViewIntent
    data object OnBackClick : ViewIntent

    // Profile Edits
    data class OnEditFieldClick(val field: EditableField) : ViewIntent
    data object OnDismissEdit : ViewIntent

    data class OnWeightChange(val weight: String) : ViewIntent
    data class OnHeightChange(val height: String) : ViewIntent
    data class OnTargetWeightChange(val targetWeight: String) : ViewIntent
    data class OnActivityLevelChange(val activityLevel: ActivityLevelDomainModel) : ViewIntent
    data class OnDietTypeChange(val diet: DietDomainModel) : ViewIntent

    data object OnSaveProfileClick : ViewIntent
}
