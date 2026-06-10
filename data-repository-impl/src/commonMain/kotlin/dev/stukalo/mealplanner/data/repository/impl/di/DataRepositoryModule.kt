package dev.stukalo.mealplanner.data.repository.impl.di

import dev.stukalo.mealplanner.data.repository.impl.RecipeRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.SearchRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.UserRepositoryImpl
import dev.stukalo.mealplanner.data.repository.impl.mapper.EdamamProductMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.FdcProductMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.OffProductMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.RecipeMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.UserMapper
import dev.stukalo.mealplanner.domain.repository.RecipeRepository
import dev.stukalo.mealplanner.domain.repository.SearchRepository
import dev.stukalo.mealplanner.domain.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataRepositoryModule = module {
    singleOf(::UserMapper)
    singleOf(::UserRepositoryImpl) bind UserRepository::class

    singleOf(::EdamamProductMapper)
    singleOf(::RecipeMapper)
    singleOf(::RecipeRepositoryImpl) bind RecipeRepository::class

    singleOf(::FdcProductMapper)
    singleOf(::OffProductMapper)
    singleOf(::SearchRepositoryImpl) bind SearchRepository::class
}
