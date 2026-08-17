package dev.stukalo.mealplanner.domain.usecase.validation

import dev.stukalo.mealplanner.core.common.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel

interface ValidateGenderUseCase {
    operator fun invoke(gender: GenderDomainModel?): ValidationResult
}
