package dev.stukalo.mealplanner.common.core.date

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

private const val DAY_MONTH_YEAR_FORMAT = "dd/MM/yyyy"

// Define your default format using the kotlinx-datetime format builder
@OptIn(FormatStringsInDatetimeFormats::class)
val dayMonthYearFormat: DateTimeFormat<LocalDate> =
    LocalDate.Format {
        byUnicodePattern(DAY_MONTH_YEAR_FORMAT)
    }
