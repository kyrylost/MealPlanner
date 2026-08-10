package dev.stukalo.mealplanner.presentation.core.platform

import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

/**
 * iOS implementation of [setLocale].
 * Updates "AppleLanguages" in [NSUserDefaults].
 */
actual fun setLocale(locale: String) {
    val languages = NSUserDefaults.standardUserDefaults.objectForKey("AppleLanguages") as? List<*>
    if (languages?.firstOrNull() != locale) {
        NSUserDefaults.standardUserDefaults.setObject(listOf(locale), "AppleLanguages")
        NSUserDefaults.standardUserDefaults.synchronize()
    }
}

actual fun getSystemLocale(): String = NSLocale.currentLocale.languageCode ?: "en"
