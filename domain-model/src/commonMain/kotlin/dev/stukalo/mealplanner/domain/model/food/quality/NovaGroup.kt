package dev.stukalo.mealplanner.domain.model.food.quality

/**
 * Represents the NOVA food processing group of a product.
 *
 * The NOVA system classifies food products into four groups:
 * - Group 1: Unprocessed or minimally processed foods.
 * - Group 2: Processed culinary ingredients.
 * - Group 3: Processed foods.
 * - Group 4: Ultra-processed food products.
 */
enum class NovaGroup {
    GROUP_1,
    GROUP_2,
    GROUP_3,
    GROUP_4
}
