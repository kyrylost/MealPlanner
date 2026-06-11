package dev.stukalo.mealplanner.domain.usecase.impl.products

import dev.stukalo.mealplanner.domain.repository.SearchRepository
import dev.stukalo.mealplanner.domain.usecase.products.GetAutoCompleteHintsUseCase

internal class GetAutoCompleteHintsUseCaseImpl(
    private val searchRepository: SearchRepository,
): GetAutoCompleteHintsUseCase {
    override suspend operator fun invoke(
        query: String,
    ): Result<List<String>> = runCatching {
        searchRepository.getAutoCompleteHints(query, HINTS_LIMIT)
    }
}

private const val HINTS_LIMIT = 10
