package dev.stukalo.mealplanner.domain.usecase.impl.di

import dev.stukalo.mealplanner.domain.usecase.GetAutoCompleteHintsUseCase
import dev.stukalo.mealplanner.domain.usecase.GetProductByBarcodeUseCase
import dev.stukalo.mealplanner.domain.usecase.GetProductsByQueryUseCase
import dev.stukalo.mealplanner.domain.usecase.GetRecipesUseCase
import dev.stukalo.mealplanner.domain.usecase.impl.products.GetAutoCompleteHintsUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.products.GetProductByBarcodeUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.products.GetProductsByQueryUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.recipes.GetRecipesUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.validation.*
import dev.stukalo.mealplanner.domain.usecase.impl.validation.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetRecipesUseCaseImpl) bind GetRecipesUseCase::class
    singleOf(::GetProductsByQueryUseCaseImpl) bind GetProductsByQueryUseCase::class
    singleOf(::GetProductByBarcodeUseCaseImpl) bind GetProductByBarcodeUseCase::class
    singleOf(::GetAutoCompleteHintsUseCaseImpl) bind GetAutoCompleteHintsUseCase::class

    singleOf(::ValidateNameUseCaseImpl) bind ValidateNameUseCase::class
    singleOf(::ValidateDateUseCaseImpl) bind ValidateDateUseCase::class
    singleOf(::ValidateHeightUseCaseImpl) bind ValidateHeightUseCase::class
    singleOf(::ValidateWeightUseCaseImpl) bind ValidateWeightUseCase::class
    singleOf(::ValidateGenderUseCaseImpl) bind ValidateGenderUseCase::class
}
