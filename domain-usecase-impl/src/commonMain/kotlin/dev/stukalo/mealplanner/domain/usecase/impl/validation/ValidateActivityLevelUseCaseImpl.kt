package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.core.common.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateActivityLevelUseCase

internal class ValidateActivityLevelUseCaseImpl : ValidateActivityLevelUseCase {
    override fun invoke(activityLevel: ActivityLevelDomainModel?): ValidationResult = if (activityLevel ==
        null
    ) {
        ValidationResult.Error(ValidationException.ActivityLevel.NotSelected())
    } else {
        ValidationResult.Success
    }
}
