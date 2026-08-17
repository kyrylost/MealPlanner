package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.core.common.date.parseDate
import dev.stukalo.mealplanner.core.common.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateDateUseCase
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

internal class ValidateDateUseCaseImpl(private val clock: Clock) : ValidateDateUseCase {
    override fun invoke(date: String?): ValidationResult {
        if (date.isNullOrBlank()) {
            return ValidationResult.Error(ValidationException.Date.Empty())
        }

        val parsedDate = date.parseDate() ?: return ValidationResult.Error(ValidationException.Date.Invalid())

        val today = clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        return if (parsedDate > today) {
            ValidationResult.Error(ValidationException.Date.Future())
        } else {
            ValidationResult.Success
        }
    }
}
