package dev.stukalo.mealplanner.presentation.feature.host

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.usecase.setting.ApplyLocaleUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.GetColorPaletteUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.GetLocaleUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.GetSystemLocaleUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.GetThemeModeUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.host.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.host.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.host.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.host.contract.ViewState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * ViewModel for the Host screen.
 * Manages global application state like theme and locale.
 */
class HostViewModel(
    getColorPaletteUseCase: GetColorPaletteUseCase,
    getThemeModeUseCase: GetThemeModeUseCase,
    getLocaleUseCase: GetLocaleUseCase,
    private val getSystemLocaleUseCase: GetSystemLocaleUseCase,
    private val applyLocaleUseCase: ApplyLocaleUseCase
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState(locale = getSystemLocaleUseCase())

    init {
        combine(
            getColorPaletteUseCase(),
            getThemeModeUseCase(),
            getLocaleUseCase()
        ) { palette, mode, locale ->
            val activeLocale = locale ?: getSystemLocaleUseCase()
            applyLocaleUseCase(activeLocale)

            PartialStateChange.ThemeConfigLoaded(
                colorPalette = palette,
                themeMode = mode,
                locale = activeLocale
            )
        }.onEach { change ->
            updateState { change.reduce(it) }
        }.launchIn(viewModelScope)
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnLocaleChanged -> {
                applyLocaleUseCase(intent.locale)
                updateState {
                    PartialStateChange.ThemeConfigLoaded(it.colorPalette, it.themeMode, intent.locale).reduce(it)
                }
            }
        }
    }
}
