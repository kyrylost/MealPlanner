package dev.stukalo.mealplanner.presentation.feature.settings.screen.contract

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

/**
 * The intents for the Settings screen.
 */
sealed interface ViewIntent : MviIntent {
    /**
     * Triggered when the user selects a new color palette.
     * @property palette The selected palette.
     */
    data class OnColorPaletteClick(val palette: ColorPaletteDomainModel) : ViewIntent

    /**
     * Triggered when the user selects a new theme mode.
     * @property mode The selected theme mode.
     */
    data class OnThemeModeClick(val mode: ThemeModeDomainModel) : ViewIntent

    /**
     * Triggered when the user selects a new language.
     * @property language The selected language code.
     */
    data class OnLanguageClick(val language: String) : ViewIntent

    /**
     * Triggered when the user clicks the back button.
     */
    data object OnBackClick : ViewIntent

    /**
     * Triggered when the user clicks on a profile field to edit it.
     * @property field The field to edit.
     */
    data class OnEditFieldClick(val field: EditableField) : ViewIntent

    /**
     * Triggered when the user dismisses the edit bottom sheet.
     */
    data object OnDismissEdit : ViewIntent

    /**
     * Triggered when the weight input changes.
     * @property weight The new weight value.
     */
    data class OnWeightChange(val weight: String) : ViewIntent

    /**
     * Triggered when the height input changes.
     * @property height The new height value.
     */
    data class OnHeightChange(val height: String) : ViewIntent

    /**
     * Triggered when the target weight input changes.
     * @property targetWeight The new target weight value.
     */
    data class OnTargetWeightChange(val targetWeight: String) : ViewIntent

    /**
     * Triggered when the physical activity level changes.
     * @property activityLevel The new activity level.
     */
    data class OnActivityLevelChange(val activityLevel: ActivityLevelDomainModel) : ViewIntent

    /**
     * Triggered when the diet type changes.
     * @property diet The new diet type.
     */
    data class OnDietTypeChange(val diet: DietDomainModel) : ViewIntent

    /**
     * Triggered when the user saves the profile changes.
     */
    data object OnSaveProfileClick : ViewIntent
}
