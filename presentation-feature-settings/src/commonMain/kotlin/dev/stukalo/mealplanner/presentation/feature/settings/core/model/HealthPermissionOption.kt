package dev.stukalo.mealplanner.presentation.feature.settings.core.model

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import org.jetbrains.compose.resources.StringResource

/**
 * Represents a UI-facing health permission option.
 *
 * @property group The permission group.
 * @property title Localized title.
 * @property description Localized description.
 * @property isGranted Whether the permission is currently granted.
 */
data class HealthPermissionOption(
    val group: HealthPermissionGroup,
    val title: StringResource,
    val description: StringResource,
    val isGranted: Boolean
)
