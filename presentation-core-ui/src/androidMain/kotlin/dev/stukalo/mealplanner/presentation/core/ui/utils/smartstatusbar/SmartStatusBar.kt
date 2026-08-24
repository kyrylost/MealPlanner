package dev.stukalo.mealplanner.presentation.core.ui.utils.smartstatusbar

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.PixelCopy
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.core.graphics.createBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "SmartStatusBar"
private const val MIN_BOUND = 0
private const val MAX_BOUND = 255

/**
 * Installs a smart status bar that automatically adjusts its icon color based on the background color
 * underneath the status bar area.
 *
 * This utility captures a screenshot of the status bar region and analyzes its brightness to
 * decide whether to use light or dark status bar icons. It is particularly useful for apps with
 * dynamic backgrounds or edge-to-edge content where the background color can change based on
 * scrolling or navigation.
 *
 * ### Usage Example
 *
 * To use this globally, call it within your `MainActivity`'s `setContent` block:
 *
 * ```kotlin
 * class MainActivity : ComponentActivity() {
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         enableEdgeToEdge()
 *         super.onCreate(savedInstanceState)
 *         setContent {
 *             InstallSmartStatusBar(
 *                 refreshPolicy = RefreshPolicy.RefreshOnInteraction()
 *             )
 *             App()
 *         }
 *     }
 *
 *     // Required if using RefreshPolicy.RefreshOnInteraction
 *     override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
 *         notifyAboutInteraction()
 *         return super.dispatchTouchEvent(ev)
 *     }
 * }
 * ```
 *
 * @param ignoreHorizontal Specifies the horizontal margin in DP (left and right) to ignore
 * when capturing the status bar area.
 * @param ignoreVertical Specifies the vertical margin in DP (top and bottom) to ignore
 * when capturing the status bar area. Use this to skip areas with shadows or unwanted artifacts.
 * @param darkColorBound A value from 0 to 255 that defines the threshold for "darkness".
 * - **0**: Only pure black is considered dark.
 * - **255**: All colors are considered dark.
 * - **Default (128)**: A balanced threshold for general use.
 * @param refreshPolicy The strategy used to trigger the background analysis.
 * See [RefreshPolicy] for available options.
 *
 * @see RefreshPolicy
 * @see notifyAboutInteraction
 */
@OptIn(FlowPreview::class)
@Composable
fun InstallSmartStatusBar(
    ignoreHorizontal: Int = 4,
    ignoreVertical: Int = 4,
    darkColorBound: Int = 128,
    refreshPolicy: RefreshPolicy
) {
    require(darkColorBound in MIN_BOUND..MAX_BOUND) { "Value must be between $MIN_BOUND and $MAX_BOUND" }

    val localDensity = LocalDensity.current
    val context = LocalActivity.current as ComponentActivity

    val darkIcons = remember {
        mutableStateOf<Boolean?>(null)
    }

    LaunchedEffect(darkIcons.value) {
        if (darkIcons.value == null) {
            return@LaunchedEffect
        }

        if (darkIcons.value == true) {
            context.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    scrim = Transparent.toArgb(),
                    darkScrim = Transparent.toArgb()
                ),
                navigationBarStyle = SystemBarStyle.light(
                    Transparent.toArgb(),
                    Transparent.toArgb()
                )
            )
        } else {
            context.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(
                    scrim = Transparent.toArgb()
                ),
                navigationBarStyle = SystemBarStyle.light(
                    Transparent.toArgb(),
                    Transparent.toArgb()
                )
            )
        }
    }

    val statusBarsHeight = WindowInsets.systemBars.getTop(localDensity)
    val fixedStatusBarHeight = remember {
        mutableIntStateOf(statusBarsHeight)
    }

    LaunchedEffect(statusBarsHeight) {
        fixedStatusBarHeight.intValue = statusBarsHeight
    }

    LaunchedEffect(Unit) {
        when (refreshPolicy) {
            is RefreshPolicy.OneTimeCheck -> {
                delay(refreshPolicy.waitBeforeCheck)

                getScreenshotAndProcess(
                    context = context,
                    density = localDensity.density,
                    statusBarHeight = fixedStatusBarHeight.intValue,
                    ignoreHorizontalDp = ignoreHorizontal,
                    ignoreVerticalDp = ignoreVertical,
                    darkColorBound = darkColorBound,
                    updateStatusBarIconColor = {
                        darkIcons.value = it
                    }
                )
            }

            is RefreshPolicy.RefreshOnInteraction -> {
                interactionFlow.debounce(refreshPolicy.debounce).collectLatest {
                    var rechecks = 0
                    val maxRechecks = refreshPolicy.recheck
                    var lastBalance: Int? = null

                    do {
                        val currentBalance = getScreenshotAndProcess(
                            context = context,
                            density = localDensity.density,
                            statusBarHeight = fixedStatusBarHeight.intValue,
                            ignoreHorizontalDp = ignoreHorizontal,
                            ignoreVerticalDp = ignoreVertical,
                            darkColorBound = darkColorBound,
                            updateStatusBarIconColor = {
                                darkIcons.value = it
                            }
                        )

                        // If balance hasn't changed, the UI is stable - stop rechecking
                        if (currentBalance != null && currentBalance == lastBalance) {
                            break
                        }
                        lastBalance = currentBalance

                        delay(refreshPolicy.waitAfterCheck)
                    } while (rechecks++ < maxRechecks)
                }
            }

            is RefreshPolicy.RefreshContinuously -> {
                while (true) {
                    getScreenshotAndProcess(
                        context = context,
                        density = localDensity.density,
                        statusBarHeight = fixedStatusBarHeight.intValue,
                        ignoreHorizontalDp = ignoreHorizontal,
                        ignoreVerticalDp = ignoreVertical,
                        darkColorBound = darkColorBound,
                        updateStatusBarIconColor = {
                            darkIcons.value = it
                        }
                    )
                    delay(refreshPolicy.waitAfterCheck)
                }
            }
        }
    }
}

private suspend fun getScreenshotAndProcess(
    context: Context,
    density: Float,
    statusBarHeight: Int,
    ignoreHorizontalDp: Int,
    ignoreVerticalDp: Int,
    darkColorBound: Int,
    updateStatusBarIconColor: (Boolean) -> Unit
): Int? {
    var attempts = 0
    val maxAttempts = 10
    var balance: Int? = null

    val window = (context as? Activity)?.window ?: return null

    while (balance == null && attempts < maxAttempts) {
        if (attempts != 0) delay(50.milliseconds)
        attempts++

        val statusBarBgBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getScreenshotFromWindow(
                window = window,
                density = density,
                statusBarHeight = statusBarHeight,
                ignoreHorizontalDp = ignoreHorizontalDp,
                ignoreVerticalDp = ignoreVerticalDp
            )
        } else {
            getScreenshotFromView(
                v = window.decorView,
                density = density,
                statusBarHeight = statusBarHeight,
                ignoreHorizontalDp = ignoreHorizontalDp,
                ignoreVerticalDp = ignoreVerticalDp
            )
        }

        statusBarBgBitmap?.let { bitmap ->
            val currentBalance = processBitmap(
                bitmap = bitmap,
                darkColorBound = darkColorBound
            )
            balance = currentBalance
            updateStatusBarIconColor(currentBalance < 0)
        }
    }
    return balance
}

private fun getScreenshotFromView(
    v: View,
    density: Float,
    statusBarHeight: Int,
    ignoreHorizontalDp: Int,
    ignoreVerticalDp: Int
): Bitmap? {
    val ignoreHorizontalPx = (ignoreHorizontalDp * density)
    val ignoreVerticalPx = (ignoreVerticalDp * density)

    try {
        val width = (v.measuredWidth - (ignoreHorizontalPx * 2)).toInt()
        val height = (statusBarHeight - (ignoreVerticalPx * 2)).toInt()

        if (width <= 0 || height <= 0) return null

        val screenshot = createBitmap(width, height)
        val canvas = Canvas(screenshot)
        canvas.translate(-ignoreHorizontalPx, -ignoreVerticalPx)
        v.draw(canvas)
        return screenshot
    } catch (e: Exception) {
        Log.e(TAG, "Failed to capture screenshot via software because: ${e.message}")
        return null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private suspend fun getScreenshotFromWindow(
    window: Window,
    density: Float,
    statusBarHeight: Int,
    ignoreHorizontalDp: Int,
    ignoreVerticalDp: Int
): Bitmap? = suspendCancellableCoroutine { continuation ->
    val ignoreHorizontalPx = (ignoreHorizontalDp * density).toInt()
    val ignoreVerticalPx = (ignoreVerticalDp * density).toInt()

    val width = window.decorView.width - (ignoreHorizontalPx * 2)
    val height = statusBarHeight - (ignoreVerticalPx * 2)

    if (width <= 0 || height <= 0) {
        continuation.resume(null)
        return@suspendCancellableCoroutine
    }

    val bitmap = createBitmap(width, height)

    val rect = Rect(
        ignoreHorizontalPx,
        ignoreVerticalPx,
        ignoreHorizontalPx + width,
        ignoreVerticalPx + height
    )

    try {
        PixelCopy.request(window, rect, bitmap, { copyResult ->
            if (copyResult == PixelCopy.SUCCESS) {
                continuation.resume(bitmap)
            } else {
                continuation.resume(null)
            }
        }, Handler(Looper.getMainLooper()))
    } catch (e: Exception) {
        Log.e(TAG, "Failed to capture screenshot because: ${e.message}")
        continuation.resume(null)
    }
}

/**
 * Determines the balance of light vs dark tones in the image.
 * Analyzes only the left and right sides (20% each) where the status bar icons are located.
 *
 * @return negative value if mostly light, positive if mostly dark.
 */
private suspend fun processBitmap(bitmap: Bitmap, darkColorBound: Int): Int {
    val width = bitmap.width
    val height = bitmap.height
    val pixels = IntArray(width * height)

    // Batch read pixels for performance
    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
    bitmap.recycle() // Release native memory immediately

    return coroutineScope {
        val sideWidth = (width * 0.2).toInt()

        // Analyze left and right zones in parallel to utilize multiple cores
        val results = listOf(
            async(Dispatchers.Default) {
                var balance = 0
                for (y in 0 until height) {
                    val rowOffset = y * width
                    for (x in 0 until sideWidth) {
                        if (pixels[rowOffset + x].isDarkColor(darkColorBound)) balance++ else balance--
                    }
                }
                balance
            },
            async(Dispatchers.Default) {
                var balance = 0
                for (y in 0 until height) {
                    val rowOffset = y * width
                    for (x in (width - sideWidth) until width) {
                        if (pixels[rowOffset + x].isDarkColor(darkColorBound)) balance++ else balance--
                    }
                }
                balance
            }
        ).awaitAll()

        results.sum()
    }
}
