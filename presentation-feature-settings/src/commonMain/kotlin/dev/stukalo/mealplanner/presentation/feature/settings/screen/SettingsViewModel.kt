package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.usecase.setting.SetThemePaletteUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val setThemePaletteUseCase: SetThemePaletteUseCase
) : ViewModel() {

    fun setTheme(palette: ColorPaletteDomainModel) {
        viewModelScope.launch {
            setThemePaletteUseCase(palette)
        }
    }
}
