package dev.stukalo.mealplanner.domain.usecase.setting

import kotlinx.coroutines.flow.Flow

interface GetLocaleUseCase {
    operator fun invoke(): Flow<String>
}
