package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDailyProgressUseCaseTest {

    private class FakeNutritionRepository : NutritionRepository {
        var progress: DailyProgressDomainModel? = null

        override fun getDailyProgressAsFlow(date: LocalDate): Flow<DailyProgressDomainModel?> = flowOf(progress)

        override fun getDailyNormAsFlow(): Flow<DailyNormDomainModel?> = TODO()
        override fun getProgressByPeriodAsFlow(
            startDate: LocalDate,
            endDate: LocalDate
        ): Flow<List<DailyProgressDomainModel>> = TODO()
        override suspend fun saveDailyNorm(dailyNorm: DailyNormDomainModel): Result<Unit> = TODO()
        override suspend fun saveDailyProgress(progress: DailyProgressDomainModel): Result<Unit> = TODO()
    }

    @Test
    fun `invoke returns progress for date from repository`() = runTest {
        val date = LocalDate(2026, 8, 26)
        val repository = FakeNutritionRepository()
        val expectedProgress = DailyProgressDomainModel(date, 1500.0, 100.0, 50.0, 150.0)
        repository.progress = expectedProgress
        val useCase = GetDailyProgressUseCaseImpl(repository)

        val result = useCase(date).first()

        assertEquals(expectedProgress, result)
    }
}
