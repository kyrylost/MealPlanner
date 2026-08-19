package dev.stukalo.mealplanner.data.health.model

/**
 * Data model representing the status of a group of health permissions.
 *
 * @property id Unique identifier for the permission group.
 * @property isGranted Whether the permissions in this group are granted.
 */
data class HealthPermissionStatusDataModel(val id: String, val isGranted: Boolean)
