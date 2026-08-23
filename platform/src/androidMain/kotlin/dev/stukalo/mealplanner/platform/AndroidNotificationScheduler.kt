package dev.stukalo.mealplanner.platform

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.meal_reminder_body
import dev.stukalo.mealplanner.core.localization.meal_reminder_title
import dev.stukalo.mealplanner.domain.service.NotificationScheduler
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.getString
import java.util.concurrent.TimeUnit
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Android implementation of [NotificationScheduler] using [WorkManager].
 */
@OptIn(ExperimentalTime::class)
internal class AndroidNotificationScheduler(private val context: Context, private val clock: Clock) :
    NotificationScheduler {

    override suspend fun scheduleMealReminder(id: Int, hour: Int, minute: Int) {
        val workManager = WorkManager.getInstance(context)

        val title = getString(Res.string.meal_reminder_title)
        val body = getString(Res.string.meal_reminder_body)

        val now = clock.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val targetTime = LocalTime(hour, minute)

        var targetDateTime = now.date.atTime(targetTime)
        if (targetDateTime <= now) {
            targetDateTime = now.date.plus(1, DateTimeUnit.DAY).atTime(targetTime)
        }

        val delay: Duration = targetDateTime.toInstant(TimeZone.currentSystemDefault()) - clock.now()

        val inputData = Data.Builder()
            .putInt(ReminderWorker.KEY_ID, id)
            .putString(ReminderWorker.KEY_TITLE, title)
            .putString(ReminderWorker.KEY_BODY, body)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay.inWholeMilliseconds, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .addTag(WORK_TAG)
            .build()

        workManager.enqueueUniqueWork(
            "$WORK_NAME_PREFIX$id",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    override fun cancelAllReminders() {
        WorkManager.getInstance(context).cancelAllWorkByTag(WORK_TAG)
    }

    override fun hasPermission(): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }

    companion object {
        private const val WORK_TAG = "meal_reminders"
        private const val WORK_NAME_PREFIX = "meal_reminder_"
    }
}
