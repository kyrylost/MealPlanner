import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.stukalo.mealplanner.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Meal Planner",
    ) {
        App()
    }
}
