package dev.stukalo.mealplanner.presentation.feature.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.onboarding_slide1_desc
import dev.stukalo.mealplanner.core.localization.onboarding_slide1_title
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.feature.onboarding.core.model.OnboardingSlideModel
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import mealplanner.presentation_feature_onboarding.generated.resources.Res as LocalRes

private const val PLACEHOLDER_HEIGHT_MULTIPLIER = 2

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun SlideImage(slide: OnboardingSlideModel, modifier: Modifier = Modifier) {
    val animationPath = slide.animationPath
    if (animationPath != null) {
        val composition by rememberLottieComposition {
            val json = LocalRes.readBytes(animationPath).decodeToString()
            LottieCompositionSpec.JsonString(json)
        }

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = Compottie.IterateForever
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            if (composition != null) {
                Image(
                    painter = rememberLottiePainter(
                        composition = composition,
                        progress = { progress },
                        enableMergePaths = true
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    } else {
        // Placeholder for animation
        Box(
            modifier =
            modifier
                .height(Theme.spacing.space128 * PLACEHOLDER_HEIGHT_MULTIPLIER)
                .background(Theme.color.surface.variant, RoundedCornerShape(Theme.radius.radius16))
        )
    }
}

@Preview
@Composable
private fun SlideImagePreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            SlideImage(
                slide = OnboardingSlideModel(
                    title = Res.string.onboarding_slide1_title,
                    description = Res.string.onboarding_slide1_desc
                ),
                modifier = Modifier.padding(Theme.spacing.space24)
            )
        }
    }
}
