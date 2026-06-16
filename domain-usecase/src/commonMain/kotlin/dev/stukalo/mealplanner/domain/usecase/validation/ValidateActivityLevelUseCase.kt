package dev.stukalo.mealplanner.domain.usecase.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel

interface ValidateActivityLevelUseCase {
    operator fun invoke(activityLevel: ActivityLevelDomainModel?): ValidationResult
}
