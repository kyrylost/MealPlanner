package dev.stukalo.mealplanner.domain.usecase.impl.di

import dev.stukalo.mealplanner.domain.usecase.impl.nutrition.GetDailyNormUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.nutrition.GetDailyProgressUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.nutrition.LogProductConsumedUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.nutrition.LogRecipeConsumedUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.nutrition.UpdateDailyProgressUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.nutrition.UpdateNutrientProgressUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.products.GetAutoCompleteHintsUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.products.GetProductByBarcodeUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.products.GetProductsByQueryUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.recipes.GetRecipeByIdUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.recipes.GetRecipesUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.recipes.GetRecommendedRecipesUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.setting.GetColorPaletteUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.setting.GetLocaleUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.setting.SetLocaleUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.setting.SetThemePaletteUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.slot.GetMealScheduleUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.slot.TrackMealConsumedUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.statistics.CalculateStreakUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.statistics.GetStatisticsUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.statistics.GetWeightHistoryUseCaseImpl
import dev.stukalo.mealplanner.domain.usecase.impl.statistics.SaveWeightUseCaseImpl
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
import dev.stukalo.mealplanner.domain.usecase.nutrition.UpdateNutrientProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.products.GetAutoCompleteHintsUseCase
import dev.stukalo.mealplanner.domain.usecase.products.GetProductByBarcodeUseCase
import dev.stukalo.mealplanner.domain.usecase.products.GetProductsByQueryUseCase
import dev.stukalo.mealplanner.domain.usecase.products.LogProductConsumedUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipeByIdUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipesUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecommendedRecipesUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.LogRecipeConsumedUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.GetColorPaletteUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.GetLocaleUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.SetLocaleUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.SetThemePaletteUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.GetMealScheduleUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.TrackMealConsumedUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.CalculateStreakUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.GetStatisticsUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.GetWeightHistoryUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.SaveWeightUseCase
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

val domainModule =
    module {
        single<kotlin.time.Clock> { kotlin.time.Clock.System }

        singleOf(::GetColorPaletteUseCaseImpl) bind GetColorPaletteUseCase::class
        singleOf(::SetThemePaletteUseCaseImpl) bind SetThemePaletteUseCase::class
        singleOf(::GetLocaleUseCaseImpl) bind GetLocaleUseCase::class
        singleOf(::SetLocaleUseCaseImpl) bind SetLocaleUseCase::class

        singleOf(::GetRecipesUseCaseImpl) bind GetRecipesUseCase::class
        singleOf(::GetRecommendedRecipesUseCaseImpl) bind GetRecommendedRecipesUseCase::class
        singleOf(::GetRecipeByIdUseCaseImpl) bind GetRecipeByIdUseCase::class
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
        singleOf(::UpdateNutrientProgressUseCaseImpl) bind UpdateNutrientProgressUseCase::class
        singleOf(::LogRecipeConsumedUseCaseImpl) bind LogRecipeConsumedUseCase::class
        singleOf(::LogProductConsumedUseCaseImpl) bind LogProductConsumedUseCase::class

        singleOf(::GetMealScheduleUseCaseImpl) bind GetMealScheduleUseCase::class
        singleOf(::TrackMealConsumedUseCaseImpl) bind TrackMealConsumedUseCase::class

        singleOf(::GetStatisticsUseCaseImpl) bind GetStatisticsUseCase::class
        singleOf(::GetWeightHistoryUseCaseImpl) bind GetWeightHistoryUseCase::class
        singleOf(::CalculateStreakUseCaseImpl) bind CalculateStreakUseCase::class
        singleOf(::SaveWeightUseCaseImpl) bind SaveWeightUseCase::class

        singleOf(::ValidateNameUseCaseImpl) bind ValidateNameUseCase::class
        singleOf(::ValidateDateUseCaseImpl) bind ValidateDateUseCase::class
        singleOf(::ValidateHeightUseCaseImpl) bind ValidateHeightUseCase::class
        singleOf(::ValidateWeightUseCaseImpl) bind ValidateWeightUseCase::class
        singleOf(::ValidateGenderUseCaseImpl) bind ValidateGenderUseCase::class
        singleOf(::ValidateActivityLevelUseCaseImpl) bind ValidateActivityLevelUseCase::class
        singleOf(::ValidateDietUseCaseImpl) bind ValidateDietUseCase::class
    }
