package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateGenderUseCase

internal class ValidateGenderUseCaseImpl : ValidateGenderUseCase {
    override fun invoke(gender: GenderDomainModel?): ValidationResult = if (gender == null) {
        ValidationResult.Error(ValidationException.Gender.NotSelected())
    } else {
        ValidationResult.Success
    }
}
