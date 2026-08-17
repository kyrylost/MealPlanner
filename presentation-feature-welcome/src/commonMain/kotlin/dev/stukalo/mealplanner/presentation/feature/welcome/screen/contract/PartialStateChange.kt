package dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract

import dev.stukalo.mealplanner.core.common.date.formatDate
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
import kotlin.time.Instant

internal sealed interface PartialStateChange {
    fun reduce(oldState: ViewState): ViewState

    sealed interface NameInput : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is TextChange ->
                oldState.copy(
                    nameInput = value,
                    nameErrorMessage = null
                )

            is Error ->
                oldState.copy(
                    nameErrorMessage = errorMessage
                )
        }

        data class TextChange(val value: String) : NameInput

        data class Error(val errorMessage: StringResource?) : NameInput
    }

    sealed interface DateInput : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is DateChange ->
                oldState.copy(
                    dateInput =
                    date
                        ?.let {
                            Instant
                                .fromEpochMilliseconds(it)
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                                .date
                                .formatDate()
                        }.orEmpty(),
                    dateErrorMessage = null
                )

            is Error ->
                oldState.copy(
                    dateErrorMessage = errorMessage
                )
        }

        data class DateChange(val date: Long?) : DateInput

        data class Error(val errorMessage: StringResource?) : DateInput
    }

    sealed interface HeightInput : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is ValueChange ->
                oldState.copy(
                    heightInput = value,
                    heightErrorMessage = null
                )

            is Error ->
                oldState.copy(
                    heightErrorMessage = errorMessage
                )
        }

        data class ValueChange(val value: String) : HeightInput

        data class Error(val errorMessage: StringResource?) : HeightInput
    }

    sealed interface WeightInput : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is ValueChange ->
                oldState.copy(
                    weightInput = value,
                    weightErrorMessage = null
                )

            is Error ->
                oldState.copy(
                    weightErrorMessage = errorMessage
                )
        }

        data class ValueChange(val value: String) : WeightInput

        data class Error(val errorMessage: StringResource?) : WeightInput
    }

    sealed interface TargetWeightInput : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is ValueChange ->
                oldState.copy(
                    targetWeightInput = value,
                    targetWeightErrorMessage = null
                )

            is Error ->
                oldState.copy(
                    targetWeightErrorMessage = errorMessage
                )
        }

        data class ValueChange(val value: String) : TargetWeightInput

        data class Error(val errorMessage: StringResource?) : TargetWeightInput
    }

    data class GenderChange(val gender: GenderDomainModel) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(gender = gender)
    }

    data class ActivityLevelChange(val activityLevel: ActivityLevelDomainModel) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(activityLevel = activityLevel)
    }

    data class DietChange(val diet: DietDomainModel) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(diet = diet)
    }

    data class StepChange(val step: Int) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(currentStep = step)
    }

    data class ShowDatePicker(val show: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(showDatePicker = show)
    }

    data class Loading(val isLoading: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isLoading = isLoading)
    }

    data class ValidationErrors(
        val nameErrorMessage: StringResource? = null,
        val dateErrorMessage: StringResource? = null,
        val heightErrorMessage: StringResource? = null,
        val weightErrorMessage: StringResource? = null,
        val targetWeightErrorMessage: StringResource? = null
    ) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            nameErrorMessage = nameErrorMessage,
            dateErrorMessage = dateErrorMessage,
            heightErrorMessage = heightErrorMessage,
            weightErrorMessage = weightErrorMessage,
            targetWeightErrorMessage = targetWeightErrorMessage
        )
    }
}
