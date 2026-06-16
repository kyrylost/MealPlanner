package dev.stukalo.mealplanner.domain.usecase.user

import dev.stukalo.mealplanner.domain.model.user.UserDomainModel

interface SaveUserDataUseCase {
    suspend operator fun invoke(user: UserDomainModel): Result<Unit>
}
