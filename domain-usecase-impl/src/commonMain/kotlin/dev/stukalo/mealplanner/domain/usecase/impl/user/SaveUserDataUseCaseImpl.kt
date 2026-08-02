package dev.stukalo.mealplanner.domain.usecase.impl.user

import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.repository.UserRepository
import dev.stukalo.mealplanner.domain.usecase.user.SaveUserDataUseCase

internal class SaveUserDataUseCaseImpl(private val userRepository: UserRepository) : SaveUserDataUseCase {
    override suspend fun invoke(user: UserDomainModel): Result<Unit> = userRepository.insert(user)
}
