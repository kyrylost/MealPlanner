package dev.stukalo.mealplanner.core.common.util

/**
 * A simple logger utility for the application.
 * Wraps logging logic to allow easy switching between different logging frameworks
 * (like Napier or Kermit) or disabling logs in production.
 */
object AppLogger {
    /**
     * Logs a debug message.
     *
     * @param tag The tag for the log message.
     * @param message The message to log.
     */
    fun d(tag: String, message: String) {
        println("[$tag] DEBUG: $message")
    }

    /**
     * Logs an error message.
     *
     * @param tag The tag for the log message.
     * @param message The message to log.
     * @param throwable Optional throwable to log.
     */
    fun e(tag: String, message: String, throwable: Throwable? = null) {
        println("[$tag] ERROR: $message")
        throwable?.printStackTrace()
    }
}
