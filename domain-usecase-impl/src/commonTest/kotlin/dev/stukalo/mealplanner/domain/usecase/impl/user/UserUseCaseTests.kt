package dev.stukalo.mealplanner.domain.usecase.impl.user

import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserUseCaseTests {

    private class FakeUserRepository : UserRepository {
        var usersCount = 0
        var user: UserDomainModel? = null
        var lastInsertedUser: UserDomainModel? = null

        override suspend fun count(): Int = usersCount
        override suspend fun insert(user: UserDomainModel): Result<Unit> {
            lastInsertedUser = user
            return Result.success(Unit)
        }
        override suspend fun getUser(): UserDomainModel? = user
        override fun getUserAsFlow(): Flow<UserDomainModel?> = flowOf(user)
    }

    @Test
    fun `CheckUserExistsUseCase returns true when count is positive`() = runTest {
        val repository = FakeUserRepository()
        repository.usersCount = 1
        val useCase = CheckUserExistsUseCaseImpl(repository)

        assertTrue(useCase())
    }

    @Test
    fun `CheckUserExistsUseCase returns false when count is zero`() = runTest {
        val repository = FakeUserRepository()
        repository.usersCount = 0
        val useCase = CheckUserExistsUseCaseImpl(repository)

        assertFalse(useCase())
    }

    @Test
    fun `GetUserUseCase returns user from repository`() = runTest {
        val repository = FakeUserRepository()
        val expectedUser =
            UserDomainModel(
                name = "Test", weight = 70.0, height = 170.0,
                birthDate = kotlinx.datetime.LocalDate(
                    1990,
                    1,
                    1
                ),
                targetWeight = 65.0, physicalActivity = dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel.LOW, gender = dev.stukalo.mealplanner.domain.model.user.GenderDomainModel.FEMALE, diet = dev.stukalo.mealplanner.domain.model.user.DietDomainModel.BALANCED_DIET, stepsTarget = 8000
            )
        repository.user = expectedUser
        val useCase = GetUserUseCaseImpl(repository)

        assertEquals(expectedUser, useCase().first())
    }

    @Test
    fun `SaveUserDataUseCase inserts user into repository`() = runTest {
        val repository = FakeUserRepository()
        val user =
            UserDomainModel(
                name = "Test", weight = 70.0, height = 170.0,
                birthDate = kotlinx.datetime.LocalDate(
                    1990,
                    1,
                    1
                ),
                targetWeight = 65.0, physicalActivity = dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel.LOW, gender = dev.stukalo.mealplanner.domain.model.user.GenderDomainModel.FEMALE, diet = dev.stukalo.mealplanner.domain.model.user.DietDomainModel.BALANCED_DIET, stepsTarget = 8000
            )
        val useCase = SaveUserDataUseCaseImpl(repository)

        useCase(user)

        assertEquals(user, repository.lastInsertedUser)
    }
}
