package dev.stukalo.mealplanner.presentation.feature.settings.core.model

/**
 * Represents the fields that can be edited in the settings profile section.
 */
internal sealed interface EditableField {
    /** The user's current weight. */
    data object Weight : EditableField

    /** The user's height. */
    data object Height : EditableField

    /** The user's target weight. */
    data object TargetWeight : EditableField

    /** The user's physical activity level. */
    data object ActivityLevel : EditableField

    /** The user's target diet type. */
    data object DietType : EditableField

    /** The user's target daily steps. */
    data object StepsTarget : EditableField
}
