package dev.stukalo.mealplanner.domain.usecase.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel

interface ValidateDietUseCase {
    operator fun invoke(diet: DietDomainModel?): ValidationResult
}
