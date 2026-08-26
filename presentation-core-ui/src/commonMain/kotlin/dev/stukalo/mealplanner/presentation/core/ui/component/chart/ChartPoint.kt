package dev.stukalo.mealplanner.presentation.core.ui.component.chart

/**
 * A generic data point for charts.
 * Decoupled from domain models to keep UI components pure.
 *
 * @property value The primary value of the point.
 * @property target Optional target/goal value for this point.
 * @property label The text label to display on the X-axis for this point.
 */
data class ChartPoint(val value: Double, val target: Double? = null, val label: String)
