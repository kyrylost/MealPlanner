package dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract

import dev.stukalo.mealplanner.common.core.date.formatDate
import org.jetbrains.compose.resources.StringResource
import java.util.Date

internal sealed interface PartialStateChange {
    fun reduce(oldState: ViewState): ViewState

    sealed interface NameInput : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is TextChange -> oldState.copy(
                nameInput = value,
                nameErrorMessage = null,
            )

            is Error -> oldState.copy(
                nameErrorMessage = errorMessage,
            )
        }

        data class TextChange(val value: String) : NameInput
        data class Error(val errorMessage: StringResource?) : NameInput
    }

    sealed interface DateInput : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is DateChange -> oldState.copy(
                dateInput = date?.let { Date(it).formatDate() }.orEmpty(),
                dateErrorMessage = null,
            )

            is Error -> oldState.copy(
                dateErrorMessage = errorMessage,
            )
        }

        data class DateChange(val date: Long?) : DateInput
        data class Error(val errorMessage: StringResource?) : DateInput
    }

    sealed interface HeightInput : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is ValueChange -> oldState.copy(
                heightInput = value,
                heightErrorMessage = null,
            )

            is Error -> oldState.copy(
                heightErrorMessage = errorMessage,
            )
        }

        data class ValueChange(val value: String) : HeightInput
        data class Error(val errorMessage: StringResource?) : HeightInput
    }

    sealed interface WeightInput : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is ValueChange -> oldState.copy(
                weightInput = value,
                weightErrorMessage = null,
            )

            is Error -> oldState.copy(
                weightErrorMessage = errorMessage,
            )
        }

        data class ValueChange(val value: String) : WeightInput
        data class Error(val errorMessage: StringResource?) : WeightInput
    }

    sealed interface GenderInput : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is ValueChange -> oldState.copy(
                genderInput = value,
                genderErrorMessage = null,
            )

            is Error -> oldState.copy(
                genderErrorMessage = errorMessage,
            )
        }

        data class ValueChange(val value: String) : GenderInput
        data class Error(val errorMessage: StringResource?) : GenderInput
    }

    data class ShowDatePicker(val show: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(showDatePicker = show)
    }

    data class ShowGenderPicker(val show: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(showGenderPicker = show)
    }

    data class ValidationErrors(
        val nameErrorMessage: StringResource? = null,
        val dateErrorMessage: StringResource? = null,
        val heightErrorMessage: StringResource? = null,
        val weightErrorMessage: StringResource? = null,
        val genderErrorMessage: StringResource? = null,
    ) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            nameErrorMessage = nameErrorMessage,
            dateErrorMessage = dateErrorMessage,
            heightErrorMessage = heightErrorMessage,
            weightErrorMessage = weightErrorMessage,
            genderErrorMessage = genderErrorMessage,
        )
    }
}
