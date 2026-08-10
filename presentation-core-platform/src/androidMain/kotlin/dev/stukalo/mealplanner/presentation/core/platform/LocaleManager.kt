package dev.stukalo.mealplanner.presentation.core.platform

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

/**
 * Android implementation of [setLocale].
 * Uses [AppCompatDelegate] to set application-specific locales.
 */
actual fun setLocale(locale: String) {
    val appLocales = LocaleListCompat.forLanguageTags(locale)
    AppCompatDelegate.setApplicationLocales(appLocales)
}

actual fun getSystemLocale(): String = Locale.getDefault().language
