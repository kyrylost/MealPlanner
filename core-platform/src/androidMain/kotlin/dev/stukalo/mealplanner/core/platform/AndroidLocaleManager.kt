package dev.stukalo.mealplanner.core.platform

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

/**
 * Android implementation of [LocaleManager].
 * Uses [AppCompatDelegate] to set application-specific locales.
 */
class AndroidLocaleManager : LocaleManager {
    override fun setLocale(locale: String) {
        val appLocales = LocaleListCompat.forLanguageTags(locale)
        AppCompatDelegate.setApplicationLocales(appLocales)
    }

    override fun getSystemLocale(): String = Locale.getDefault().language
}
