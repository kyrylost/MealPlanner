package dev.stukalo.mealplanner.domain.usecase.products

interface GetAutoCompleteHintsUseCase {
    suspend operator fun invoke(
        query: String,
    ): Result<List<String>>
}
