package dev.stukalo.mealplanner.presentation.feature.filters.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewIntent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FiltersScreen(
    initialFilters: FilterDomainModel? = null,
    onApplyFilters: (FilterDomainModel) -> Unit,
    onBack: () -> Unit
) {
    val viewModel: FiltersViewModel = koinViewModel()

    LaunchedEffect(initialFilters) {
        if (initialFilters != null) {
            viewModel.onIntent(ViewIntent.OnInitialFilters(initialFilters))
        }
    }

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
