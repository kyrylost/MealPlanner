package dev.stukalo.mealplanner.common.core.date

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat


/**
 * Formats a LocalDate into a String.
 * Note: kotlinx-datetime formatting won't return null, so the return type is just String.
 */
fun LocalDate.formatDate(
    format: DateTimeFormat<LocalDate> = dayMonthYearFormat
): String = format.format(this)

/**
 * Parses a String into a LocalDate.
 * Returns null if the string cannot be parsed according to the format.
 */
fun String.parseDate(
    format: DateTimeFormat<LocalDate> = dayMonthYearFormat
): LocalDate? {
    return try {
        LocalDate.parse(this, format)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    }
}