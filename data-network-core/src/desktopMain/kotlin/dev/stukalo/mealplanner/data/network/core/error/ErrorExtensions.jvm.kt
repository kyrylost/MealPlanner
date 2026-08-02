package dev.stukalo.mealplanner.data.network.core.error

import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

actual val Throwable?.isConnectionError: Boolean
    get() =
        this is ConnectException ||
            this is UnknownHostException ||
            this is SocketException ||
            this is SocketTimeoutException ||
            (this?.cause?.isConnectionError ?: false)
