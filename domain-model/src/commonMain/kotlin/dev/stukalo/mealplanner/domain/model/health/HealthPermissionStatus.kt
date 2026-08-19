package dev.stukalo.mealplanner.domain.model.health

/**
 * Domain model representing the status of a group of health permissions.
 *
 * @property group The permission group identifier.
 * @property isGranted Whether the permissions in this group are granted.
 */
data class HealthPermissionStatus(val group: HealthPermissionGroup, val isGranted: Boolean)
