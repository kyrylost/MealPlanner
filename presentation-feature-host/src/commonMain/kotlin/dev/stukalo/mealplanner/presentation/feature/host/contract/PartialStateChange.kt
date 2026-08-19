package dev.stukalo.mealplanner.presentation.feature.host.contract

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel

internal sealed interface PartialStateChange {
    fun reduce(oldState: ViewState): ViewState

    data class ThemeConfigLoaded(
        val colorPalette: ColorPaletteDomainModel?,
        val themeMode: ThemeModeDomainModel,
        val locale: String
    ) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            colorPalette = colorPalette,
            themeMode = themeMode,
            locale = locale
        )
    }
}
