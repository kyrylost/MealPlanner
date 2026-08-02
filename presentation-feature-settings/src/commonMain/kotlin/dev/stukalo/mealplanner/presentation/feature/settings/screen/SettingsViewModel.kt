package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.usecase.setting.SetThemePaletteUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewState
import kotlinx.coroutines.launch

class SettingsViewModel(private val setThemePaletteUseCase: SetThemePaletteUseCase) :
    BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnThemeClick -> {
                viewModelScope.launch {
                    setThemePaletteUseCase(intent.palette)
                }
            }
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
        }
    }
}
