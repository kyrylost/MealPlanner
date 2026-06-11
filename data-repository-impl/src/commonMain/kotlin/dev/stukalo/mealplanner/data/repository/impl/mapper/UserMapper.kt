package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.common.core.mapper.BaseMapper
import dev.stukalo.mealplanner.data.database.model.DietDatabaseModel
import dev.stukalo.mealplanner.data.database.model.GenderDatabaseModel
import dev.stukalo.mealplanner.data.database.model.UserDatabaseModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel

internal class UserMapper : BaseMapper<UserDatabaseModel, UserDomainModel> {

    override fun mapTo(model: UserDatabaseModel): UserDomainModel = with(model) {
        UserDomainModel(
            id = id,
            name = name,
            birthDate = birthDate,
            height = height,
            weight = weight,
            physicalActivity = physicalActivity,
            gender = gender.toDomain(),
            diet = diet.toDomain()
        )
    }

    override fun mapFrom(model: UserDomainModel): UserDatabaseModel = with(model) {
        UserDatabaseModel(
            id = id,
            name = name,
            birthDate = birthDate,
            height = height,
            weight = weight,
            physicalActivity = physicalActivity,
            gender = gender.toData(),
            diet = diet.toData()
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
}
