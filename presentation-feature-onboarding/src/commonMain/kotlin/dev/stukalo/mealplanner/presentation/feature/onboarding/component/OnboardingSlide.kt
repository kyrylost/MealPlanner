package dev.stukalo.mealplanner.presentation.feature.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
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
import org.jetbrains.compose.resources.stringResource
import mealplanner.presentation_feature_onboarding.generated.resources.Res as LocalRes

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun OnboardingSlide(slide: OnboardingSlideModel, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
        modifier
            .fillMaxSize()
            .padding(horizontal = Theme.spacing.space24)
    ) {
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
                contentAlignment = Alignment.BottomCenter,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
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
                Modifier
                    .fillMaxWidth()
                    .height(Theme.spacing.space128 * 2)
                    .background(Theme.color.surface.variant, RoundedCornerShape(Theme.radius.radius16))
            )
        }

        Spacer(modifier = Modifier.height(Theme.spacing.space32))

        Text(
            text = stringResource(slide.title),
            style = Theme.typography.bold36,
            color = Theme.color.text.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Theme.spacing.space16))

        Text(
            text = stringResource(slide.description),
            style = Theme.typography.regular14,
            color = Theme.color.text.secondary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun OnboardingSlidePreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            OnboardingSlide(
                slide = OnboardingSlideModel(
                    title = Res.string.onboarding_slide1_title,
                    description = Res.string.onboarding_slide1_desc
                )
            )
        }
    }
}
