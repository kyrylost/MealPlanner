package dev.stukalo.mealplanner.presentation.core.platform

import java.util.Locale

/**
 * Desktop implementation of [setLocale].
 * Updates [Locale.setDefault].
 */
actual fun setLocale(locale: String) {
    val javaLocale = Locale.forLanguageTag(locale)
    Locale.setDefault(javaLocale)
}

actual fun getSystemLocale(): String = Locale.getDefault().language
