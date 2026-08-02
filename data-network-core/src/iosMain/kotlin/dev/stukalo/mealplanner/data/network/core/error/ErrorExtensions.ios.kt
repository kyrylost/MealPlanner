package dev.stukalo.mealplanner.data.network.core.error

import io.ktor.client.engine.darwin.DarwinHttpRequestException
import platform.Foundation.NSURLErrorCannotConnectToHost
import platform.Foundation.NSURLErrorCannotFindHost
import platform.Foundation.NSURLErrorDomain
import platform.Foundation.NSURLErrorNotConnectedToInternet
import platform.Foundation.NSURLErrorTimedOut

actual val Throwable?.isConnectionError: Boolean
    get() {
        if (this == null) return false

        // If using Ktor's Darwin engine, extract the underlying NSError
        if (this is DarwinHttpRequestException) {
            if (origin.domain == NSURLErrorDomain) {
                return when (origin.code) {
                    NSURLErrorNotConnectedToInternet,
                    NSURLErrorTimedOut,
                    NSURLErrorCannotFindHost,
                    NSURLErrorCannotConnectToHost
                    -> true
                    else -> false
                }
            }
        }

        return this.cause?.isConnectionError ?: false
    }
