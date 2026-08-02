package dev.stukalo.mealplanner.presentation.feature.gateway.screen

import dev.stukalo.mealplanner.domain.usecase.setting.GetColorPaletteUseCase
import dev.stukalo.mealplanner.domain.usecase.user.CheckUserExistsUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.gateway.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.gateway.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.gateway.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.gateway.screen.contract.ViewState
import kotlinx.coroutines.flow.first

internal class GatewayViewModel(
    private val checkUserExistsUseCase: CheckUserExistsUseCase,
    private val getColorPaletteUseCase: GetColorPaletteUseCase
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    init {
        onIntent(ViewIntent.CheckUserExistence)
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.CheckUserExistence -> checkUserExistence()
        }
    }

    private suspend fun checkUserExistence() {
        updateState { PartialStateChange.Loading(true).reduce(it) }

        getColorPaletteUseCase().first()

        if (checkUserExistsUseCase()) {
            sendEvent(ViewEvent.NavigateToMain)
        } else {
            sendEvent(ViewEvent.NavigateToWelcome)
        }

        updateState { PartialStateChange.Loading(false).reduce(it) }
    }
}
