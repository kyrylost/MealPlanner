package dev.stukalo.mealplanner.domain.usecase.impl.user

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_CARB_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_FAT_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_PROTEIN_GRAM
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.usecase.user.CalculateDailyNormUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

/**
 * Implementation of [CalculateDailyNormUseCase] using the Harris-Benedict formula
 * and activity coefficients.
 *
 * @property clock Clock provider for age calculation.
 */
internal class CalculateDailyNormUseCaseImpl(private val clock: Clock) : CalculateDailyNormUseCase {

    override suspend fun invoke(user: UserDomainModel): DailyNormDomainModel = withContext(Dispatchers.Default) {
        val today = clock.todayIn(TimeZone.currentSystemDefault())
        var age = today.year - user.birthDate.year
        if (today.month < user.birthDate.month ||
            (today.month == user.birthDate.month && today.day < user.birthDate.day)
        ) {
            age--
        }

        val activityCoefficient = when (user.physicalActivity) {
            ActivityLevelDomainModel.VERY_LOW -> ACTIVITY_VERY_LOW
            ActivityLevelDomainModel.LOW -> ACTIVITY_LOW
            ActivityLevelDomainModel.MEDIUM -> ACTIVITY_MEDIUM
            ActivityLevelDomainModel.HIGH -> ACTIVITY_HIGH
            ActivityLevelDomainModel.VERY_HIGH -> ACTIVITY_VERY_HIGH
        }

        var calories = if (user.gender == GenderDomainModel.MALE) {
            when (age) {
                in AGE_GROUP_YOUNG_START..AGE_GROUP_YOUNG_END ->
                    (MALE_YOUNG_COEFF * user.weight + MALE_YOUNG_CONST) * MJ_TO_KCAL
                in AGE_GROUP_ADULT_START..AGE_GROUP_ADULT_END ->
                    (MALE_ADULT_COEFF * user.weight + MALE_ADULT_CONST) * MJ_TO_KCAL
                else ->
                    (MALE_SENIOR_COEFF * user.weight + MALE_SENIOR_CONST) * MJ_TO_KCAL
            }
        } else {
            when (age) {
                in AGE_GROUP_YOUNG_START..AGE_GROUP_YOUNG_END ->
                    (FEMALE_YOUNG_COEFF * user.weight + FEMALE_YOUNG_CONST) * MJ_TO_KCAL
                in AGE_GROUP_ADULT_START..AGE_GROUP_ADULT_END ->
                    (FEMALE_ADULT_COEFF * user.weight + FEMALE_ADULT_CONST) * MJ_TO_KCAL
                else ->
                    (FEMALE_SENIOR_COEFF * user.weight + FEMALE_SENIOR_CONST) * MJ_TO_KCAL
            }
        }
        calories *= activityCoefficient

        val (proteinsCoefficient, fatsCoefficient, carbsCoefficient) = when (user.diet) {
            DietDomainModel.BALANCED_DIET -> Triple(BALANCED_PROTEIN, BALANCED_FAT, BALANCED_CARB)
            DietDomainModel.WEIGHT_GAIN -> {
                calories *= GAIN_CALORIE_SURPLUS
                Triple(GAIN_PROTEIN, GAIN_FAT, GAIN_CARB)
            }
            DietDomainModel.WEIGHT_LOSS -> {
                calories *= LOSS_CALORIE_DEFICIT
                Triple(LOSS_PROTEIN, LOSS_FAT, LOSS_CARB)
            }
            DietDomainModel.CUTTING_DIET -> {
                calories *= CUTTING_CALORIE_DEFICIT
                Triple(CUTTING_PROTEIN, CUTTING_FAT, CUTTING_CARB)
            }
        }

        DailyNormDomainModel(
            calories = calories,
            proteins = (calories * proteinsCoefficient) / CALORIES_PER_PROTEIN_GRAM,
            fats = (calories * fatsCoefficient) / CALORIES_PER_FAT_GRAM,
            carbohydrates = (calories * carbsCoefficient) / CALORIES_PER_CARB_GRAM
        )
    }

    companion object {
        private const val MJ_TO_KCAL = 240.0

        private const val AGE_GROUP_YOUNG_START = 0
        private const val AGE_GROUP_YOUNG_END = 30
        private const val AGE_GROUP_ADULT_START = 31
        private const val AGE_GROUP_ADULT_END = 60

        private const val MALE_YOUNG_COEFF = 0.0630
        private const val MALE_YOUNG_CONST = 2.8957
        private const val MALE_ADULT_COEFF = 0.0491
        private const val MALE_ADULT_CONST = 2.4587
        private const val MALE_SENIOR_COEFF = 0.0491
        private const val MALE_SENIOR_CONST = 1.8988

        private const val FEMALE_YOUNG_COEFF = 0.0621
        private const val FEMALE_YOUNG_CONST = 2.0357
        private const val FEMALE_ADULT_COEFF = 0.0342
        private const val FEMALE_ADULT_CONST = 3.5377
        private const val FEMALE_SENIOR_COEFF = 0.0377
        private const val FEMALE_SENIOR_CONST = 2.7545

        private const val ACTIVITY_VERY_LOW = 1.1
        private const val ACTIVITY_LOW = 1.3
        private const val ACTIVITY_MEDIUM = 1.5
        private const val ACTIVITY_HIGH = 1.7
        private const val ACTIVITY_VERY_HIGH = 1.9

        private const val BALANCED_PROTEIN = 0.2
        private const val BALANCED_FAT = 0.2
        private const val BALANCED_CARB = 0.6

        private const val GAIN_CALORIE_SURPLUS = 1.1
        private const val GAIN_PROTEIN = 0.28
        private const val GAIN_FAT = 0.2
        private const val GAIN_CARB = 0.52

        private const val LOSS_CALORIE_DEFICIT = 0.8
        private const val LOSS_PROTEIN = 0.31
        private const val LOSS_FAT = 0.29
        private const val LOSS_CARB = 0.41

        private const val CUTTING_CALORIE_DEFICIT = 0.85
        private const val CUTTING_PROTEIN = 0.5
        private const val CUTTING_FAT = 0.2
        private const val CUTTING_CARB = 0.3
    }
}
