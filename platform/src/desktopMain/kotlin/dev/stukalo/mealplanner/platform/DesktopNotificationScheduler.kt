package dev.stukalo.mealplanner.platform

import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.app_name
import dev.stukalo.mealplanner.core.localization.meal_reminder_body
import dev.stukalo.mealplanner.core.localization.meal_reminder_title
import dev.stukalo.mealplanner.domain.service.NotificationScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.getString
import java.awt.AWTException
import java.awt.SystemTray
import java.awt.TrayIcon
import java.awt.image.BufferedImage
import java.util.concurrent.ConcurrentHashMap
import javax.swing.SwingUtilities
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Desktop implementation of [NotificationScheduler] using [SystemTray].
 */
@OptIn(ExperimentalTime::class)
internal class DesktopNotificationScheduler(private val clock: Clock) : NotificationScheduler {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val scheduledJobs = ConcurrentHashMap<Int, Job>()

    private val trayIcon: TrayIcon? by lazy {
        if (!SystemTray.isSupported()) return@lazy null

        val tray = SystemTray.getSystemTray()

        // Use a transparent 1x1 image as a placeholder for the tray icon
        val image = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        val trayIcon = TrayIcon(image)
        trayIcon.isImageAutoSize = true

        try {
            tray.add(trayIcon)
            trayIcon
        } catch (_: AWTException) {
            null
        }
    }

    init {
        // Trigger lazy initialization of TrayIcon
        val icon = trayIcon

        scope.launch {
            try {
                val appName = getString(Res.string.app_name)
                SwingUtilities.invokeLater {
                    icon?.toolTip = appName
                }
            } catch (_: Exception) {
                // Silently fail if resources are not yet available
            }
        }
    }

    override suspend fun scheduleMealReminder(id: Int, hour: Int, minute: Int) {
        val title = getString(Res.string.meal_reminder_title)
        val body = getString(Res.string.meal_reminder_body)

        scheduledJobs[id]?.cancel()

        scheduledJobs[id] = scope.launch {
            while (isActive) {
                val now = clock.now().toLocalDateTime(TimeZone.currentSystemDefault())
                val targetTime = LocalTime(hour, minute)
                var targetDateTime = now.date.atTime(targetTime)

                if (targetDateTime <= now) {
                    targetDateTime = now.date.plus(NEXT_DAY, DateTimeUnit.DAY).atTime(targetTime)
                }

                val delayDuration: Duration = targetDateTime.toInstant(TimeZone.currentSystemDefault()) - clock.now()
                delay(delayDuration)

                if (isActive) {
                    trayIcon?.displayMessage(title, body, TrayIcon.MessageType.INFO)
                }
            }
        }
    }

    override fun cancelAllReminders() {
        scheduledJobs.values.forEach { it.cancel() }
        scheduledJobs.clear()
    }

    override fun hasPermission(): Boolean = true

    companion object {
        private const val NEXT_DAY = 1
    }
}
