package dev.stukalo.mealplanner.presentation.feature.settings.screen.contract

import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

data class ViewState(
    val user: UserDomainModel? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val currentLanguage: String = "",
    val editingField: EditableField? = null,
    val tempWeightInput: String = "",
    val tempHeightInput: String = "",
    val tempTargetWeightInput: String = ""
) : MviViewState

sealed interface EditableField {
    data object Weight : EditableField
    data object Height : EditableField
    data object TargetWeight : EditableField
    data object ActivityLevel : EditableField
    data object DietType : EditableField
}
