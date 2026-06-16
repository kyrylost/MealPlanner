package dev.stukalo.mealplanner.common.core.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatDate(
    format: String = DAY_MONTH_YEAR_FORMAT
): String? = SimpleDateFormat(format, Locale.getDefault()).format(this)

fun String.parseDate(
    format: String = DAY_MONTH_YEAR_FORMAT
): Date? = SimpleDateFormat(format, Locale.getDefault()).parse(this)
