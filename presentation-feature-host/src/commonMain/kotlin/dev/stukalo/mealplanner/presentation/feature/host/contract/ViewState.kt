package dev.stukalo.mealplanner.presentation.feature.host.contract

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

/**
 * View state for the Host feature.
 */
data class ViewState(val themePalette: ColorPaletteDomainModel? = null, val locale: String = "") : MviViewState
