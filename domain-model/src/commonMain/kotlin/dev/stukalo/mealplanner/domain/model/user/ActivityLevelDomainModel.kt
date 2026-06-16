package dev.stukalo.mealplanner.domain.model.user

/**
 * Represents the physical activity level of a user.
 */
enum class ActivityLevelDomainModel {
    /**
     * Very Low: sedentary work, no training.
     */
    VERY_LOW,

    /**
     * Low: sedentary work, 1-2 trainings per week.
     */
    LOW,

    /**
     * Medium: sedentary work 3-4 trainings per week, or active work with no trainings.
     */
    MEDIUM,

    /**
     * High: sedentary work 4-7 trainings per week, or active work with 1-3 trainings per week.
     */
    HIGH,

    /**
     * Very High: sedentary work and 7+ trainings per week, or active work with 4+ trainings per week.
     */
    VERY_HIGH
}
