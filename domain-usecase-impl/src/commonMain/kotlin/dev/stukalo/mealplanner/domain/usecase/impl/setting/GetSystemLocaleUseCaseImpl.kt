package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.service.LocaleManager
import dev.stukalo.mealplanner.domain.usecase.setting.GetSystemLocaleUseCase

internal class GetSystemLocaleUseCaseImpl(private val localeManager: LocaleManager) : GetSystemLocaleUseCase {
    override fun invoke(): String = localeManager.getSystemLocale()
}
