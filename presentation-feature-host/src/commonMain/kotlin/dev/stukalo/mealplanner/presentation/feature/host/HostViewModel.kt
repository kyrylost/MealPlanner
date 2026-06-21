package dev.stukalo.mealplanner.presentation.feature.host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.usecase.setting.GetThemePaletteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HostViewModel(
    private val getThemePaletteUseCase: GetThemePaletteUseCase
) : ViewModel() {

    val themePalette: StateFlow<ColorPaletteDomainModel> = getThemePaletteUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ColorPaletteDomainModel.ORANGE
        )
}
