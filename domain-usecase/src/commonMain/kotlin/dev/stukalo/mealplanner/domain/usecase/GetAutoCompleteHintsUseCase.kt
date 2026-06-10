package dev.stukalo.mealplanner.domain.usecase

interface GetAutoCompleteHintsUseCase {
    suspend operator fun invoke(
        query: String,
    ): Result<List<String>>
}
