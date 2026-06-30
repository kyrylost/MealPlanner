package dev.stukalo.mealplanner.domain.usecase.impl.di

import dev.stukalo.mealplanner.domain.usecase.impl.nutrition.GetDailyNormUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.nutrition.GetDailyProgressUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.nutrition.UpdateDailyProgressUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.products.GetAutoCompleteHintsUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.products.GetProductByBarcodeUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.products.GetProductsByQueryUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.recipes.GetRecipesUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.setting.GetColorPaletteUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.setting.GetLocaleUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.setting.SetLocaleUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.setting.SetThemePaletteUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.user.CheckUserExistsUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.user.GetUserUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.user.SaveDailyNormUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.user.SaveUserDataUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.validation.ValidateActivityLevelUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.validation.ValidateDateUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.validation.ValidateDietUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.validation.ValidateGenderUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.validation.ValidateHeightUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.validation.ValidateNameUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.validation.ValidateWeightUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.UpdateDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.products.GetAutoCompleteHintsUseCase
import dev.stukalo.mealplanner.domain.usecase.products.GetProductByBarcodeUseCase
import dev.stukalo.mealplanner.domain.usecase.products.GetProductsByQueryUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipesUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.GetColorPaletteUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.GetLocaleUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.SetLocaleUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.SetThemePaletteUseCase
import dev.stukalo.mealplanner.domain.usecase.user.CheckUserExistsUseCase
import dev.stukalo.mealplanner.domain.usecase.user.GetUserUseCase
import dev.stukalo.mealplanner.domain.usecase.user.SaveDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.user.SaveUserDataUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateActivityLevelUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateDateUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateDietUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateGenderUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateHeightUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateNameUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateWeightUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetColorPaletteUseCaseImpl) bind GetColorPaletteUseCase::class
    singleOf(::SetThemePaletteUseCaseImpl) bind SetThemePaletteUseCase::class
    singleOf(::GetLocaleUseCaseImpl) bind GetLocaleUseCase::class
    singleOf(::SetLocaleUseCaseImpl) bind SetLocaleUseCase::class

    singleOf(::GetRecipesUseCaseImpl) bind GetRecipesUseCase::class
    singleOf(::GetProductsByQueryUseCaseImpl) bind GetProductsByQueryUseCase::class
    singleOf(::GetProductByBarcodeUseCaseImpl) bind GetProductByBarcodeUseCase::class
    singleOf(::GetAutoCompleteHintsUseCaseImpl) bind GetAutoCompleteHintsUseCase::class

    singleOf(::SaveUserDataUseCaseImpl) bind SaveUserDataUseCase::class
    singleOf(::SaveDailyNormUseCaseImpl) bind SaveDailyNormUseCase::class
    singleOf(::CheckUserExistsUseCaseImpl) bind CheckUserExistsUseCase::class
    singleOf(::GetUserUseCaseImpl) bind GetUserUseCase::class

    singleOf(::GetDailyNormUseCaseImpl) bind GetDailyNormUseCase::class
    singleOf(::GetDailyProgressUseCaseImpl) bind GetDailyProgressUseCase::class
    singleOf(::UpdateDailyProgressUseCaseImpl) bind UpdateDailyProgressUseCase::class

    singleOf(::ValidateNameUseCaseImpl) bind ValidateNameUseCase::class
    singleOf(::ValidateDateUseCaseImpl) bind ValidateDateUseCase::class
    singleOf(::ValidateHeightUseCaseImpl) bind ValidateHeightUseCase::class
    singleOf(::ValidateWeightUseCaseImpl) bind ValidateWeightUseCase::class
    singleOf(::ValidateGenderUseCaseImpl) bind ValidateGenderUseCase::class
    singleOf(::ValidateActivityLevelUseCaseImpl) bind ValidateActivityLevelUseCase::class
    singleOf(::ValidateDietUseCaseImpl) bind ValidateDietUseCase::class
}
