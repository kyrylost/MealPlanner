package dev.stukalo.mealplanner.presentation.core.ui.widget.button.text.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import dev.stukalo.mealplanner.common.core.ext.has
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.core.ButtonColor
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.core.ButtonColorSet
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.core.ButtonInteractionState

class TextButtonColor(private val buttonColorSet: ButtonColorSet) : ButtonColor {
    @Composable
    override fun borderColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color> =
        rememberUpdatedState(buttonColorSet.borderColorDefault)

    @Composable
    override fun foregroundColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color> =
        rememberUpdatedState(
            with(buttonColorSet) {
                when {
                    !enabled -> foregroundColorDisabled
                    interactionState has ButtonInteractionState.PRESSED -> foregroundColorPressed
                    else -> foregroundColorDefault
                }
            }
        )

    @Composable
    override fun backgroundColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color> =
        rememberUpdatedState(
            with(buttonColorSet) {
                when {
                    !enabled -> backgroundColorDisabled
                    interactionState has ButtonInteractionState.PRESSED -> backgroundColorPressed
                    else -> backgroundColorDefault
                }
            }
        )
}
