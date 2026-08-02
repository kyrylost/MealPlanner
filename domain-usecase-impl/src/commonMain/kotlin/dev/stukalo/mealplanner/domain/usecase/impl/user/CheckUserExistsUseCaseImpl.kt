package dev.stukalo.mealplanner.domain.usecase.impl.user

import dev.stukalo.mealplanner.domain.repository.UserRepository
import dev.stukalo.mealplanner.domain.usecase.user.CheckUserExistsUseCase

internal class CheckUserExistsUseCaseImpl(private val userRepository: UserRepository) : CheckUserExistsUseCase {
    override suspend fun invoke(): Boolean = userRepository.count() > 0
}
