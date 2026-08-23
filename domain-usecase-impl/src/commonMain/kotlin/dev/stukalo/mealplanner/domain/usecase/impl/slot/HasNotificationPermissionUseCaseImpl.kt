package dev.stukalo.mealplanner.domain.usecase.impl.slot

import dev.stukalo.mealplanner.domain.service.NotificationScheduler
import dev.stukalo.mealplanner.domain.usecase.slot.HasNotificationPermissionUseCase

internal class HasNotificationPermissionUseCaseImpl(private val notificationScheduler: NotificationScheduler) :
    HasNotificationPermissionUseCase {
    override fun invoke(): Boolean = notificationScheduler.hasPermission()
}
