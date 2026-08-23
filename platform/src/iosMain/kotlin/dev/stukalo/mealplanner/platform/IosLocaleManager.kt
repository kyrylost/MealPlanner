package dev.stukalo.mealplanner.platform

import dev.stukalo.mealplanner.domain.service.LocaleManager
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

/**
 * iOS implementation of [LocaleManager].
 * Updates "AppleLanguages" in [NSUserDefaults].
 */
class IosLocaleManager : LocaleManager {
    override fun setLocale(locale: String) {
        val languages = NSUserDefaults.standardUserDefaults.objectForKey("AppleLanguages") as? List<*>
        if (languages?.firstOrNull() != locale) {
            NSUserDefaults.standardUserDefaults.setObject(listOf(locale), "AppleLanguages")
            NSUserDefaults.standardUserDefaults.synchronize()
        }
    }

    override fun getSystemLocale(): String = NSLocale.currentLocale.languageCode
}
