package dev.stukalo.mealplanner.domain.service

/**
 * Interface for platform-specific notification scheduling.
 */
interface NotificationScheduler {
    /**
     * Schedules a meal reminder notification.
     *
     * @param id Unique ID for the notification.
     * @param hour Hour of the day (0-23).
     * @param minute Minute of the hour (0-59).
     */
    suspend fun scheduleMealReminder(id: Int, hour: Int, minute: Int)

    /**
     * Cancels all scheduled meal reminders.
     */
    fun cancelAllReminders()

    /**
     * Checks if the application has permission to post notifications.
     */
    fun hasPermission(): Boolean
}
