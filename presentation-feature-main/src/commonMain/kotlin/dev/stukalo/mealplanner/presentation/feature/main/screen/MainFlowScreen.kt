package dev.stukalo.mealplanner.presentation.feature.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalBottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.haze.hazeSource
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.feature.main.component.MealPlannerBottomNavigationBar
import dev.stukalo.mealplanner.presentation.feature.main.navigation.ext.navigateTab
import dev.stukalo.mealplanner.presentation.feature.main.navigation.inner.InnerMainNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.main.navigation.inner.MainTab

@Composable
fun MainFlowScreen(appNavController: NavHostController, initialTab: NavigationDirection) {
    val mainNavController = rememberNavController()
    var bottomBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val hazeState = rememberHazeState()

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val selectedTab =
        MainTab.entries.find { tab ->
            currentDestination?.hasRoute(tab.route::class) == true
        } ?: MainTab.Home

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CompositionLocalProvider(LocalBottomBarHeight provides bottomBarHeight) {
            InnerMainNavigationGraph(
                mainNavController = mainNavController,
                appNavController = appNavController,
                startDestination = initialTab,
                modifier =
                Modifier
                    .fillMaxSize()
                    .hazeSource(hazeState)
                    .background(Theme.color.background.primary) // workaround to make bottom nav blur work
            )
        }

        MealPlannerBottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = { tab ->
                val isPopped =
                    mainNavController.popBackStack(
                        route = tab.route,
                        inclusive = false
                    )
                if (!isPopped) {
                    mainNavController.navigateTab(tab.route)
                }
            },
            hazeState = hazeState,
            modifier =
            Modifier
                .align(Alignment.BottomCenter)
                .onGloballyPositioned {
                    bottomBarHeight = with(density) { it.size.height.toDp() }
                }
        )
    }
}
