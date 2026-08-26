package dev.stukalo.mealplanner.domain.usecase.impl.user

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class SaveDailyNormUseCaseTest {

    private class FakeNutritionRepository : NutritionRepository {
        var lastSavedNorm: DailyNormDomainModel? = null

        override suspend fun saveDailyNorm(dailyNorm: DailyNormDomainModel): Result<Unit> {
            lastSavedNorm = dailyNorm
            return Result.success(Unit)
        }

        override fun getDailyNormAsFlow(): Flow<DailyNormDomainModel?> = TODO()
        override fun getDailyProgressAsFlow(date: LocalDate): Flow<DailyProgressDomainModel?> = TODO()
        override fun getProgressByPeriodAsFlow(
            startDate: LocalDate,
            endDate: LocalDate
        ): Flow<List<DailyProgressDomainModel>> = TODO()
        override suspend fun saveDailyProgress(progress: DailyProgressDomainModel): Result<Unit> = TODO()
    }

    @Test
    fun `invoke saves norm to repository`() = runTest {
        val repository = FakeNutritionRepository()
        val norm = DailyNormDomainModel(2000.0, 150.0, 70.0, 200.0)
        val useCase = SaveDailyNormUseCaseImpl(repository)

        useCase(norm)

        assertEquals(norm, repository.lastSavedNorm)
    }
}
