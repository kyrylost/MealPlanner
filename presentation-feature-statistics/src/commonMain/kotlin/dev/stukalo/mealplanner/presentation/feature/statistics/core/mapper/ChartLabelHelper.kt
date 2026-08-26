package dev.stukalo.mealplanner.presentation.feature.statistics.core.mapper

import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

/**
 * Helper object to generate descriptive labels for chart axes.
 */
internal object ChartLabelHelper {
    /**
     * Generates a label for a given date based on the total number of points in the chart.
     *
     * @param date The date for which the label is generated.
     * @param totalPoints The total number of points in the dataset.
     * @param days Localized day names.
     * @param months Localized month names.
     * @param dateFormat The short date format string.
     * @return A formatted label string.
     */
    fun getLabel(
        date: LocalDate,
        totalPoints: Int,
        days: List<String>,
        months: List<String>,
        dateFormat: String
    ): String = when {
        totalPoints <= 7 -> days[date.dayOfWeek.ordinal]
        totalPoints <= 12 -> months[date.month.ordinal]
        else ->
            dateFormat
                .replace("%1\$d", date.day.toString())
                .replace("%2\$d", date.month.number.toString())
    }
}
