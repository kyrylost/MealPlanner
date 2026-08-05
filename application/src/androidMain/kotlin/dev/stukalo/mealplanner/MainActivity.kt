package dev.stukalo.mealplanner

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import dev.stukalo.mealplanner.presentation.core.ui.utils.smartstatusbar.InstallSmartStatusBar
import dev.stukalo.mealplanner.presentation.core.ui.utils.smartstatusbar.RefreshPolicy
import dev.stukalo.mealplanner.presentation.core.ui.utils.smartstatusbar.notifyAboutInteraction
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            InstallSmartStatusBar(
                darkColorBound = 136,
                refreshPolicy = RefreshPolicy.RefreshOnInteraction()
            )

            App {
                androidLogger(Level.DEBUG)
                androidContext(this@MainActivity)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        lifecycleScope.launch {
            notifyAboutInteraction()
        }
        return super.dispatchTouchEvent(ev)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
