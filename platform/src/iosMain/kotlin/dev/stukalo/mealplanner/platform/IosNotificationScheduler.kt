package dev.stukalo.mealplanner.platform

import dev.stukalo.mealplanner.core.common.util.AppLogger
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.meal_reminder_body
import dev.stukalo.mealplanner.core.localization.meal_reminder_title
import dev.stukalo.mealplanner.domain.service.NotificationScheduler
import org.jetbrains.compose.resources.getString
import platform.Foundation.NSDateComponents
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNUserNotificationCenter

/**
 * iOS implementation of [NotificationScheduler] using [UNUserNotificationCenter].
 */
internal class IosNotificationScheduler : NotificationScheduler {
    override suspend fun scheduleMealReminder(id: Int, hour: Int, minute: Int) {
        val center = UNUserNotificationCenter.currentNotificationCenter()

        val title = getString(Res.string.meal_reminder_title)
        val body = getString(Res.string.meal_reminder_body)

        // Request permission if not already granted
        center.requestAuthorizationWithOptions(
            UNAuthorizationOptionAlert or UNAuthorizationOptionSound
        ) { granted, _ ->
            if (granted) {
                schedule(center, id, title, body, hour, minute)
            }
        }
    }

    private fun schedule(
        center: UNUserNotificationCenter,
        id: Int,
        title: String,
        body: String,
        hour: Int,
        minute: Int
    ) {
        val content = UNMutableNotificationContent().apply {
            setTitle(title)
            setBody(body)
            setSound(UNNotificationSound.defaultSound())
        }

        val dateComponents = NSDateComponents().apply {
            setHour(hour.toLong())
            setMinute(minute.toLong())
        }

        val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(
            dateComponents,
            repeats = true
        )
        val request = UNNotificationRequest.requestWithIdentifier(
            id.toString(),
            content,
            trigger
        )

        center.addNotificationRequest(request) { error ->
            if (error != null) {
                AppLogger.e(
                    "IosNotificationScheduler",
                    "Failed to add notification request",
                    Exception(error.localizedDescription)
                )
            }
        }
    }

    override fun cancelAllReminders() {
        UNUserNotificationCenter.currentNotificationCenter().removeAllPendingNotificationRequests()
    }

    override fun hasPermission(): Boolean {
        // On iOS, we can request permission every time we schedule, or use this to gate UI.
        // Since getNotificationSettings is async and doesn't map perfectly to sync check,
        // we'll return true here and handle authorization in scheduleMealReminder.
        return true
    }
}
