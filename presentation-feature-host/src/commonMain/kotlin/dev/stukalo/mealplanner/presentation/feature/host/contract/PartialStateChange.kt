package dev.stukalo.mealplanner.presentation.feature.host.contract

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviPartialStateChange

internal sealed interface PartialStateChange : MviPartialStateChange<ViewState> {
    override fun reduce(oldState: ViewState): ViewState

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

    data class LocaleChanged(val locale: String) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(locale = locale)
    }
}
