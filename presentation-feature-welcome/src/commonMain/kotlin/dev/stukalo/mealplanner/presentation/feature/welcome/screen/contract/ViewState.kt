package dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract

import androidx.compose.runtime.Immutable
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState
import dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.SnackbarModel
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
    val genderInput: String = "",
    val genderErrorMessage: StringResource? = null,
    val showDatePicker: Boolean = false,
    val showGenderPicker: Boolean = false,
    val snackbarModel: SnackbarModel? = null,
) : MviViewState
