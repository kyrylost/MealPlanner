package dev.stukalo.mealplanner.presentation.core.ui.widget.chart

/**
 * Defines the visual representation of the [StatisticsChart].
 */
enum class ChartStyle {
    /**
     * Data points are represented as vertical bars.
     * Suitable for comparing discrete values (e.g., daily calorie intake).
     */
    BAR,

    /**
     * Data points are connected by a continuous line.
     * Suitable for showing trends over time (e.g., weight history).
     */
    LINE
}
