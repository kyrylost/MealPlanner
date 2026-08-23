package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.service.LocaleManager
import dev.stukalo.mealplanner.domain.usecase.setting.ApplyLocaleUseCase

internal class ApplyLocaleUseCaseImpl(private val localeManager: LocaleManager) : ApplyLocaleUseCase {
    override fun invoke(locale: String) {
        localeManager.setLocale(locale)
    }
}
