package dev.stukalo.mealplanner.presentation.feature.host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.usecase.setting.GetColorPaletteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HostViewModel(getColorPaletteUseCase: GetColorPaletteUseCase) : ViewModel() {
    val themePalette: StateFlow<ColorPaletteDomainModel?> =
        getColorPaletteUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = null
            )
}
