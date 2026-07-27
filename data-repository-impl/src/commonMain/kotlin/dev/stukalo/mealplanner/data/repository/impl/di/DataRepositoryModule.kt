package dev.stukalo.mealplanner.data.repository.impl.di

import dev.stukalo.mealplanner.data.repository.impl.MealScheduleRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.NutritionRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.RecipeRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.SearchRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.UserRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.mapper.DailyNormMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.DailyProgressMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.EdamamProductMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.FdcProductMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.MealSlotMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.MealTypeMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.OffProductMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.RecipeMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.UserMapper
import dev.stukalo.mealplanner.data.repository.impl.setting.SettingsRepositoryImpl
import dev.stukalo.mealplanner.domain.repository.MealScheduleRepository
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.repository.RecipeRepository
import dev.stukalo.mealplanner.domain.repository.SearchRepository
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataRepositoryModule = module {
    singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class

    singleOf(::UserMapper)
    singleOf(::UserRepositoryImpl) bind UserRepository::class

    singleOf(::DailyNormMapper)
    singleOf(::DailyProgressMapper)
    singleOf(::NutritionRepositoryImpl) bind NutritionRepository::class

    singleOf(::EdamamProductMapper)
    singleOf(::RecipeMapper)
    singleOf(::RecipeRepositoryImpl) bind RecipeRepository::class

    singleOf(::MealTypeMapper)
    singleOf(::MealSlotMapper)
    singleOf(::MealScheduleRepositoryImpl) bind MealScheduleRepository::class

    singleOf(::FdcProductMapper)
    singleOf(::OffProductMapper)
    singleOf(::SearchRepositoryImpl) bind SearchRepository::class
}
