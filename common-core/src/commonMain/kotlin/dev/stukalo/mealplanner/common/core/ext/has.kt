package dev.stukalo.mealplanner.common.core.ext

/**
 * Checks if the specified [bit] flag is set in this integer.
 *
 * This is typically used for working with bit flags, where each bit in the integer
 * represents a specific boolean condition or state.
 *
 * Example:
 * ```
 * val flags = 0b0101
 * if (flags has 0b0001) {
 *  // Bit 0 is set
 * }
 * ```
 *
 * @param bit The bit flag to check for.
 * @return `true` if the bit is set; `false` otherwise.
 */
infix fun Int.has(bit: Int) = this.and(bit) != 0
