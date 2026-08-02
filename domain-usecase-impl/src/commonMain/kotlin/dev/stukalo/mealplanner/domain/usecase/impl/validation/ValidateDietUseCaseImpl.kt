package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateDietUseCase

internal class ValidateDietUseCaseImpl : ValidateDietUseCase {
    override fun invoke(diet: DietDomainModel?): ValidationResult = if (diet == null) {
        ValidationResult.Error(ValidationException.Diet.NotSelected())
    } else {
        ValidationResult.Success
    }
}
