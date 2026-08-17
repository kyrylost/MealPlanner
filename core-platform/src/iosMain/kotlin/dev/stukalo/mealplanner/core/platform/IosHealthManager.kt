package dev.stukalo.mealplanner.core.platform

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

/**
 * iOS implementation of [HealthManager].
 */
class IosHealthManager : HealthManager {
    override fun openHealthSettings() {
        val settingsUrl = NSURL.URLWithString(UIApplicationOpenSettingsURLString)
        if (settingsUrl != null && UIApplication.sharedApplication.canOpenURL(settingsUrl)) {
            UIApplication.sharedApplication.openURL(settingsUrl)
        }
    }

    override fun installHealthConnect() {
        // Not applicable on iOS.
    }
}
