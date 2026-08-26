package dev.stukalo.mealplanner.data.repository.impl.di

import dev.stukalo.mealplanner.data.repository.impl.health.HealthRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.health.mapper.HealthPermissionStatusMapper
import dev.stukalo.mealplanner.data.repository.impl.health.mapper.HealthPermissionTypeMapper
import dev.stukalo.mealplanner.data.repository.impl.health.mapper.HealthServiceStatusMapper
import dev.stukalo.mealplanner.data.repository.impl.product.SearchRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.product.mapper.FDCDetailsProductMapper
import dev.stukalo.mealplanner.data.repository.impl.product.mapper.FDCSearchProductMapper
import dev.stukalo.mealplanner.data.repository.impl.product.mapper.OFFProductMapper
import dev.stukalo.mealplanner.data.repository.impl.recipe.RecipeRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.recipe.mapper.EdamamRecipeToProductMapper
import dev.stukalo.mealplanner.data.repository.impl.recipe.mapper.RecipeMapper
import dev.stukalo.mealplanner.data.repository.impl.setting.SettingsRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.slot.MealSlotRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.slot.mapper.MealSlotMapper
import dev.stukalo.mealplanner.data.repository.impl.slot.mapper.MealTypeMapper
import dev.stukalo.mealplanner.data.repository.impl.statistics.NutritionRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.statistics.WeightRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.statistics.mapper.DailyNormMapper
import dev.stukalo.mealplanner.data.repository.impl.statistics.mapper.DailyProgressMapper
import dev.stukalo.mealplanner.data.repository.impl.statistics.mapper.WeightHistoryMapper
import dev.stukalo.mealplanner.data.repository.impl.user.UserRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.user.mapper.UserMapper
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.repository.MealSlotRepository
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.repository.RecipeRepository
import dev.stukalo.mealplanner.domain.repository.SearchRepository
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.repository.UserRepository
import dev.stukalo.mealplanner.domain.repository.WeightRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataRepositoryModule =
    module {
        singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class

        singleOf(::UserMapper)
        singleOf(::UserRepositoryImpl) bind UserRepository::class

        singleOf(::DailyNormMapper)
        singleOf(::DailyProgressMapper)
        singleOf(::NutritionRepositoryImpl) bind NutritionRepository::class

        singleOf(::WeightHistoryMapper)
        singleOf(::WeightRepositoryImpl) bind WeightRepository::class

        singleOf(::HealthServiceStatusMapper)
        singleOf(::HealthPermissionTypeMapper)
        singleOf(::HealthPermissionStatusMapper)
        singleOf(::HealthRepositoryImpl) bind HealthRepository::class

        singleOf(::EdamamRecipeToProductMapper)
        singleOf(::RecipeMapper)
        singleOf(::RecipeRepositoryImpl) bind RecipeRepository::class

        singleOf(::MealTypeMapper)
        singleOf(::MealSlotMapper)
        singleOf(::MealSlotRepositoryImpl) bind MealSlotRepository::class

        singleOf(::FDCSearchProductMapper)
        singleOf(::FDCDetailsProductMapper)
        singleOf(::OFFProductMapper)
        singleOf(::SearchRepositoryImpl) bind SearchRepository::class
    }
