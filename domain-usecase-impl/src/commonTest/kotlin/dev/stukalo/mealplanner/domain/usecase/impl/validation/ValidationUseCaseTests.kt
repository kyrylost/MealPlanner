package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.core.common.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import kotlin.test.Test
import kotlin.test.assertTrue

class ValidationUseCaseTests {

    @Test
    fun `ValidateNameUseCase returns error for empty name`() {
        val useCase = ValidateNameUseCaseImpl()
        val result = useCase("")
        assertTrue(result is ValidationResult.Error && result.exception is ValidationException.Name.Empty)
    }

    @Test
    fun `ValidateNameUseCase returns success for valid name`() {
        val useCase = ValidateNameUseCaseImpl()
        val result = useCase("John Doe")
        assertTrue(result is ValidationResult.Success)
    }

    @Test
    fun `ValidateWeightUseCase returns error for null weight`() {
        val useCase = ValidateWeightUseCaseImpl()
        val result = useCase(null)
        assertTrue(result is ValidationResult.Error && result.exception is ValidationException.Weight.Empty)
    }

    @Test
    fun `ValidateWeightUseCase returns error for weight out of range`() {
        val useCase = ValidateWeightUseCaseImpl()
        val result = useCase(5.0) // Too low
        assertTrue(result is ValidationResult.Error && result.exception is ValidationException.Weight.Invalid)
    }
}
