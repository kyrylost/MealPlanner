package dev.stukalo.mealplanner.presentation.feature.host

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.usecase.setting.GetColorPaletteUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.GetLocaleUseCase
import dev.stukalo.mealplanner.presentation.core.platform.getSystemLocale
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.host.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.host.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.host.contract.ViewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * ViewModel for the Host screen.
 * Manages global application state like theme and locale.
 */
class HostViewModel(getColorPaletteUseCase: GetColorPaletteUseCase, getLocaleUseCase: GetLocaleUseCase) :
    BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState(locale = getSystemLocale())

    init {
        getColorPaletteUseCase()
            .onEach { palette ->
                updateState { it.copy(themePalette = palette) }
            }.launchIn(viewModelScope)

        getLocaleUseCase()
            .onEach { locale ->
                updateState { it.copy(locale = locale ?: getSystemLocale()) }
            }.launchIn(viewModelScope)
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnLocaleChanged -> {
                updateState { it.copy(locale = intent.locale) }
            }
        }
    }
}
