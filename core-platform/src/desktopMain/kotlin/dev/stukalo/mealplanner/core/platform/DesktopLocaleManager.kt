package dev.stukalo.mealplanner.core.platform

import java.util.Locale

/**
 * Desktop implementation of [LocaleManager].
 * Updates [Locale.setDefault].
 */
class DesktopLocaleManager : LocaleManager {
    override fun setLocale(locale: String) {
        val javaLocale = Locale.forLanguageTag(locale)
        Locale.setDefault(javaLocale)
    }

    override fun getSystemLocale(): String = Locale.getDefault().language
}
