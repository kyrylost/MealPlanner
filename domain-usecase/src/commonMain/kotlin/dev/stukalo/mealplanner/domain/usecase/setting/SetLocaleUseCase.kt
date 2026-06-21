package dev.stukalo.mealplanner.domain.usecase.setting

interface SetLocaleUseCase {
    suspend operator fun invoke(locale: String)
}
