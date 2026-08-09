package dev.stukalo.mealplanner.presentation.core.ui.widget.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.haze.HazeState
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconClock
import dev.stukalo.mealplanner.presentation.core.ui.widget.card.BlurredCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeCard(
    title: String,
    imageUrl: String?,
    timeText: String?,
    healthLabels: List<String>?,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BlurredCard(
        modifier = modifier,
        hazeState = hazeState,
        shape = Theme.shape.normalRoundedCornerShape
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            // Image Section
            Box(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(Theme.aspect.recipeCard)
                    .background(Theme.color.backgroundSecondary)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Health Labels Overlay
                healthLabels?.let { labels ->
                    FlowRow(
                        modifier =
                        Modifier
                            .align(Alignment.TopStart)
                            .padding(Theme.spacing.space8),
                        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space4),
                        verticalArrangement = Arrangement.spacedBy(Theme.spacing.space4),
                        maxLines = 2
                    ) {
                        labels.forEach { label ->
                            HealthLabel(label)
                        }
                    }
                }
            }

            // Content Section
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Theme.spacing.space12),
                verticalArrangement = Arrangement.spacedBy(Theme.spacing.space24)
            ) {
                Text(
                    text = title,
                    style = Theme.typography.bold14,
                    color = Theme.color.textPrimary,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = IconClock,
                        contentDescription = null,
                        modifier = Modifier.size(Theme.spacing.space12),
                        tint = Theme.color.textSecondary
                    )
                    Spacer(modifier = Modifier.width(Theme.spacing.space4))
                    Text(
                        text = timeText ?: "--",
                        style = Theme.typography.regular12,
                        color = Theme.color.textSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun HealthLabel(label: String) {
    Surface(
        color = Theme.color.primary.copy(alpha = 0.8f),
        shape = Theme.shape.normalRoundedCornerShape
    ) {
        Text(
            text = label,
            style = Theme.typography.bold12,
            color = Color.White,
            modifier = Modifier.padding(
                horizontal = Theme.spacing.space8,
                vertical = Theme.spacing.space2
            )
        )
    }
}

@Preview
@Composable
private fun RecipeCardPreview() {
    Theme {
        RecipeCard(
            title = "Healthy Salmon Salad",
            imageUrl = null,
            timeText = "25m",
            healthLabels = listOf("Keto", "Low-Carb"),
            hazeState = rememberHazeState(),
            onClick = {}
        )
    }
}
