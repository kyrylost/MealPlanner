package dev.stukalo.mealplanner.presentation.feature.settings.screen.contract

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

/**
 * The view state for the Settings screen.
 *
 * @property user The current user profile data.
 * @property isLoading Whether the screen is currently loading data.
 * @property isSaving Whether a profile update is in progress.
 * @property currentLanguage The currently selected language code (e.g., "en", "uk").
 * @property currentColorPalette The currently selected color palette.
 * @property currentThemeMode The currently selected theme mode (Auto, Light, Dark).
 * @property editingField The field currently being edited in a bottom sheet, or null.
 * @property tempWeightInput Temporary input for weight editing.
 * @property tempHeightInput Temporary input for height editing.
 * @property tempTargetWeightInput Temporary input for target weight editing.
 */
data class ViewState(
    val user: UserDomainModel? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val currentLanguage: String = "",
    val currentColorPalette: ColorPaletteDomainModel = ColorPaletteDomainModel.ORANGE,
    val currentThemeMode: ThemeModeDomainModel = ThemeModeDomainModel.AUTO,
    val editingField: EditableField? = null,
    val tempWeightInput: String = "",
    val tempHeightInput: String = "",
    val tempTargetWeightInput: String = ""
) : MviViewState {
    companion object {
        /** Language code for English. */
        const val LOCALE_EN = "en"

        /** Language code for Ukrainian. */
        const val LOCALE_UK = "uk"
    }
}

/**
 * Represents the fields that can be edited in the settings profile section.
 */
sealed interface EditableField {
    /** The user's current weight. */
    data object Weight : EditableField

    /** The user's height. */
    data object Height : EditableField

    /** The user's target weight. */
    data object TargetWeight : EditableField

    /** The user's physical activity level. */
    data object ActivityLevel : EditableField

    /** The user's target diet type. */
    data object DietType : EditableField
}
