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

class GetDailyNormUseCaseTest {

    private class FakeNutritionRepository : NutritionRepository {
        var norm: DailyNormDomainModel? = null

        override fun getDailyNormAsFlow(): Flow<DailyNormDomainModel?> = flowOf(norm)

        override fun getDailyProgressAsFlow(date: LocalDate): Flow<DailyProgressDomainModel?> = TODO()
        override fun getProgressByPeriodAsFlow(
            startDate: LocalDate,
            endDate: LocalDate
        ): Flow<List<DailyProgressDomainModel>> = TODO()
        override suspend fun saveDailyNorm(dailyNorm: DailyNormDomainModel): Result<Unit> = TODO()
        override suspend fun saveDailyProgress(progress: DailyProgressDomainModel): Result<Unit> = TODO()
    }

    @Test
    fun `invoke returns norm from repository`() = runTest {
        val repository = FakeNutritionRepository()
        val expectedNorm = DailyNormDomainModel(2000.0, 150.0, 60.0, 200.0)
        repository.norm = expectedNorm
        val useCase = GetDailyNormUseCaseImpl(repository)

        val result = useCase().first()

        assertEquals(expectedNorm, result)
    }
}
