package dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract

import androidx.compose.runtime.Immutable
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import org.jetbrains.compose.resources.StringResource

@Immutable
internal data class ViewState(
    val nameInput: String = "",
    val nameErrorMessage: StringResource? = null,
    val dateInput: String = "",
    val dateErrorMessage: StringResource? = null,
    val heightInput: String = "",
    val heightErrorMessage: StringResource? = null,
    val weightInput: String = "",
    val weightErrorMessage: StringResource? = null,
    val gender: GenderDomainModel? = null,
    val showDatePicker: Boolean = false,
) : MviViewState
