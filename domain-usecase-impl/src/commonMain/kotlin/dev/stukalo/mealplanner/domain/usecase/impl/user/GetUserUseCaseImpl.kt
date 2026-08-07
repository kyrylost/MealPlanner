package dev.stukalo.mealplanner.domain.usecase.impl.user

import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.repository.UserRepository
import dev.stukalo.mealplanner.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of [GetUserUseCase] for single-user mode.
 */
class GetUserUseCaseImpl(private val userRepository: UserRepository) : GetUserUseCase {
    override fun invoke(): Flow<UserDomainModel?> = userRepository.getUserAsFlow()
}
