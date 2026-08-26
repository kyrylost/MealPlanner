package dev.stukalo.mealplanner.presentation.feature.barcodescanner.core.mapper

import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.barcode_scanner_not_found
import dev.stukalo.mealplanner.domain.model.exception.ProductException
import org.jetbrains.compose.resources.StringResource

/**
 * Maps [ProductException] to a localized [StringResource].
 */
fun ProductException.toMessage(): StringResource = when (this) {
    is ProductException.ProductNotFound -> Res.string.barcode_scanner_not_found
}
