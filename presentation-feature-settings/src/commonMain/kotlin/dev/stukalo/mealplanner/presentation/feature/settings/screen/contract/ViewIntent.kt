package dev.stukalo.mealplanner.presentation.feature.settings.screen.contract

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

sealed interface ViewIntent : MviIntent {
    data class OnThemeClick(val palette: ColorPaletteDomainModel) : ViewIntent

    data object OnBackClick : ViewIntent
}
