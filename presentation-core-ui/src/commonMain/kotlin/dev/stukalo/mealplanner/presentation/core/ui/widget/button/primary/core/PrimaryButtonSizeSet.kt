package dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonSize
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonSizeSet

class PrimaryButtonSizeSet : ButtonSizeSet {
    override fun small(): ButtonSize = object : ButtonSize {
        override val iconSize
            @Composable
            get() = 16.dp
        override val borderSize
            @Composable
            get() = 0.dp
        override val contentPadding
            @Composable
            get() =
                PaddingValues(
                    horizontal = 8.dp,
                    vertical = 8.dp
                )
        override val spacing
            @Composable
            get() = 8.dp
        override val minHeight
            @Composable
            get() = 32.dp
        override val loadingSize
            @Composable
            get() = 16.dp
    }

    override fun default(): ButtonSize = object : ButtonSize {
        override val iconSize
            @Composable
            get() = 16.dp
        override val borderSize
            @Composable
            get() = 0.dp
        override val contentPadding
            @Composable
            get() =
                PaddingValues(
                    horizontal = 24.dp,
                    vertical = 12.dp
                )
        override val spacing
            @Composable
            get() = 8.dp
        override val minHeight
            @Composable
            get() = 40.dp
        override val loadingSize
            @Composable
            get() = 16.dp
    }
}
