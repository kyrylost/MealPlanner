package dev.stukalo.mealplanner.domain.usecase.user

fun interface CheckUserExistsUseCase {
    suspend operator fun invoke(): Boolean
}
