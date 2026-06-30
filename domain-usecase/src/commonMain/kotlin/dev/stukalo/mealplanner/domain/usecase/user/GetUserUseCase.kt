package dev.stukalo.mealplanner.domain.usecase.user

import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface GetUserUseCase {
    operator fun invoke(): Flow<UserDomainModel?>
}
