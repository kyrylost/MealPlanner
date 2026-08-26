package dev.stukalo.mealplanner.presentation.feature.filters.screen

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * The main entry point for the Filters feature.
 *
 * @param initialFilters The filters to show when the screen opens.
 * @param onApplyFilters Callback when the user applies the filters.
 * @param onBack Callback when the user navigates back.
 */
@Composable
internal fun FiltersScreen(
    initialFilters: FilterDomainModel? = null,
    onApplyFilters: (FilterDomainModel) -> Unit,
    onBack: () -> Unit
) {
    val viewModel: FiltersViewModel = koinViewModel { parametersOf(initialFilters) }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                is ViewEvent.ApplyFilters -> onApplyFilters(event.filters)
                ViewEvent.NavigateBack -> onBack()
            }
        }
    ) { state ->
        FiltersContent(
            state = state,
            onIntent = viewModel::onIntent
        )
    }
}

@Preview
@Composable
private fun FiltersScreenPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            FiltersContent(
                state = ViewState(),
                onIntent = {}
            )
        }
    }
}
