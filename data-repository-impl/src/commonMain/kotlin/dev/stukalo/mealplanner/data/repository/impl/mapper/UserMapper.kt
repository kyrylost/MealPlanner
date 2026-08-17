package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.data.database.model.user.ActivityLevelDatabaseModel
import dev.stukalo.mealplanner.data.database.model.user.DietDatabaseModel
import dev.stukalo.mealplanner.data.database.model.user.GenderDatabaseModel
import dev.stukalo.mealplanner.data.database.model.user.UserDatabaseModel
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel

/**
 * Mapper for [UserDatabaseModel] and [UserDomainModel].
 * Weight is handled externally because it resides in a separate table.
 */
internal class UserMapper : BaseMapper<UserDatabaseModel, UserDomainModel> {
    /**
     * Maps database model to domain model.
     *
     * @param model The database model.
     * @param weight The current weight of the user (from weight history).
     * @return The domain model.
     */
    fun mapTo(model: UserDatabaseModel, weight: Double): UserDomainModel = with(model) {
        UserDomainModel(
            id = id,
            name = name,
            birthDate = birthDate,
            height = height,
            weight = weight,
            targetWeight = targetWeight,
            physicalActivity = physicalActivity.toDomain(),
            gender = gender.toDomain(),
            diet = diet.toDomain(),
            stepsTarget = stepsTarget
        )
    }

    /**
     * Maps domain model to database model.
     *
     * @param model The domain model.
     * @return The database model (weight is ignored).
     */
    override fun mapFrom(model: UserDomainModel): UserDatabaseModel = with(model) {
        UserDatabaseModel(
            id = id,
            name = name,
            birthDate = birthDate,
            height = height,
            targetWeight = targetWeight,
            physicalActivity = physicalActivity.toData(),
            gender = gender.toData(),
            diet = diet.toData(),
            stepsTarget = stepsTarget
        )
    }

    private fun GenderDatabaseModel.toDomain(): GenderDomainModel = when (this) {
        GenderDatabaseModel.MALE -> GenderDomainModel.MALE
        GenderDatabaseModel.FEMALE -> GenderDomainModel.FEMALE
    }

    private fun GenderDomainModel.toData(): GenderDatabaseModel = when (this) {
        GenderDomainModel.MALE -> GenderDatabaseModel.MALE
        GenderDomainModel.FEMALE -> GenderDatabaseModel.FEMALE
    }

    private fun DietDatabaseModel.toDomain(): DietDomainModel = when (this) {
        DietDatabaseModel.BALANCED_DIET -> DietDomainModel.BALANCED_DIET
        DietDatabaseModel.WEIGHT_GAIN -> DietDomainModel.WEIGHT_GAIN
        DietDatabaseModel.WEIGHT_LOSS -> DietDomainModel.WEIGHT_LOSS
        DietDatabaseModel.CUTTING_DIET -> DietDomainModel.CUTTING_DIET
    }

    private fun DietDomainModel.toData(): DietDatabaseModel = when (this) {
        DietDomainModel.BALANCED_DIET -> DietDatabaseModel.BALANCED_DIET
        DietDomainModel.WEIGHT_GAIN -> DietDatabaseModel.WEIGHT_GAIN
        DietDomainModel.WEIGHT_LOSS -> DietDatabaseModel.WEIGHT_LOSS
        DietDomainModel.CUTTING_DIET -> DietDatabaseModel.CUTTING_DIET
    }

    private fun ActivityLevelDatabaseModel.toDomain(): ActivityLevelDomainModel = when (this) {
        ActivityLevelDatabaseModel.VERY_LOW -> ActivityLevelDomainModel.VERY_LOW
        ActivityLevelDatabaseModel.LOW -> ActivityLevelDomainModel.LOW
        ActivityLevelDatabaseModel.MEDIUM -> ActivityLevelDomainModel.MEDIUM
        ActivityLevelDatabaseModel.HIGH -> ActivityLevelDomainModel.HIGH
        ActivityLevelDatabaseModel.VERY_HIGH -> ActivityLevelDomainModel.VERY_HIGH
    }

    private fun ActivityLevelDomainModel.toData(): ActivityLevelDatabaseModel = when (this) {
        ActivityLevelDomainModel.VERY_LOW -> ActivityLevelDatabaseModel.VERY_LOW
        ActivityLevelDomainModel.LOW -> ActivityLevelDatabaseModel.LOW
        ActivityLevelDomainModel.MEDIUM -> ActivityLevelDatabaseModel.MEDIUM
        ActivityLevelDomainModel.HIGH -> ActivityLevelDatabaseModel.HIGH
        ActivityLevelDomainModel.VERY_HIGH -> ActivityLevelDatabaseModel.VERY_HIGH
    }
}
