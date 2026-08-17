package dev.stukalo.mealplanner.core.platform

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.health.connect.client.HealthConnectClient

/**
 * Android implementation of [HealthManager].
 *
 * @property context Android context used to start the settings activity.
 */
class AndroidHealthManager(private val context: Context) : HealthManager {
    override fun openHealthSettings() {
        val intent = Intent(HealthConnectClient.ACTION_HEALTH_CONNECT_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun installHealthConnect() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "market://details?id=com.google.android.apps.healthdata&url=healthconnect%3A%2F%2Fonboarding".toUri()
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
