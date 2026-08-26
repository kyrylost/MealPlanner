package dev.stukalo.mealplanner.presentation.feature.statistics.core.mapper

import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_apr
import dev.stukalo.mealplanner.core.localization.common_aug
import dev.stukalo.mealplanner.core.localization.common_dec
import dev.stukalo.mealplanner.core.localization.common_feb
import dev.stukalo.mealplanner.core.localization.common_format_date_short
import dev.stukalo.mealplanner.core.localization.common_fri
import dev.stukalo.mealplanner.core.localization.common_jan
import dev.stukalo.mealplanner.core.localization.common_jul
import dev.stukalo.mealplanner.core.localization.common_jun
import dev.stukalo.mealplanner.core.localization.common_mar
import dev.stukalo.mealplanner.core.localization.common_may
import dev.stukalo.mealplanner.core.localization.common_mon
import dev.stukalo.mealplanner.core.localization.common_nov
import dev.stukalo.mealplanner.core.localization.common_oct
import dev.stukalo.mealplanner.core.localization.common_sat
import dev.stukalo.mealplanner.core.localization.common_sep
import dev.stukalo.mealplanner.core.localization.common_sun
import dev.stukalo.mealplanner.core.localization.common_thu
import dev.stukalo.mealplanner.core.localization.common_tue
import dev.stukalo.mealplanner.core.localization.common_wed
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsPoint
import dev.stukalo.mealplanner.presentation.core.ui.component.chart.ChartPoint
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun List<StatisticsPoint>.toChartPoints(): List<ChartPoint> {
    val localizedDays = listOf(
        stringResource(Res.string.common_mon),
        stringResource(Res.string.common_tue),
        stringResource(Res.string.common_wed),
        stringResource(Res.string.common_thu),
        stringResource(Res.string.common_fri),
        stringResource(Res.string.common_sat),
        stringResource(Res.string.common_sun)
    )

    val localizedMonths = listOf(
        stringResource(Res.string.common_jan),
        stringResource(Res.string.common_feb),
        stringResource(Res.string.common_mar),
        stringResource(Res.string.common_apr),
        stringResource(Res.string.common_may),
        stringResource(Res.string.common_jun),
        stringResource(Res.string.common_jul),
        stringResource(Res.string.common_aug),
        stringResource(Res.string.common_sep),
        stringResource(Res.string.common_oct),
        stringResource(Res.string.common_nov),
        stringResource(Res.string.common_dec)
    )

    val dateFormat = stringResource(Res.string.common_format_date_short)

    return map { point ->
        ChartPoint(
            value = point.value,
            target = point.target,
            label = ChartLabelHelper.getLabel(point.date, size, localizedDays, localizedMonths, dateFormat)
        )
    }
}
