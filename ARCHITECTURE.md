# Project Architecture

This document describes the high-level design and architectural patterns used in the MealPlanner project.

## High-Level Architecture
The project follows a **Modular Clean Architecture** with **MVI (Model-View-Intent)** in the presentation layer.

### Presentation Layer (MVI)
All feature modules MUST follow the MVI pattern and be structured similarly to `:presentation-feature-welcome`.

#### 1. Package Structure
- `di/`: Koin module definition (`singleOf`, `viewModelOf`).
- `navigation/`: Navigation graph using `composable<NavigationDirection.Feature>`.
- `component/`: Reusable `@Composable` UI elements specific to this feature (e.g., sections, headers, cards).
- `core/`: Internal models (`model/`), mappers (`mapper/`), or platform-specific logic (`platform/`).
- `screen/`:
    - `contract/`: The MVI contract.
    - `[Feature]Screen.kt`: The logic holder and Koin entry point.
    - `[Feature]Content.kt`: Stateless UI renderer.
    - `[Feature]ViewModel.kt`: Business logic and state management.

#### 2. MVI Contract (`screen/contract/`)
- `ViewState`: Immutable data class representing the entire UI state.
- `ViewIntent`: Sealed interface for user actions or system triggers.
- `ViewEvent`: Sealed interface for one-time events (navigation, snackbars).
- **`PartialStateChange`**: A mandatory sealed interface (or class) with a `reduce(currentState: ViewState): ViewState` function. It encapsulates how specific actions update the `ViewState`.
    - **Simple Changes**: For atomic updates (e.g., toggling a loader or changing a simple value), use a `data class` or `object` directly.
        ```kotlin
        data class Loading(val isLoading: Boolean) : PartialStateChange {
            override fun reduce(oldState: ViewState) = oldState.copy(isLoading = isLoading)
        }
        ```
    - **Grouped Logic**: For related fields or complex inputs (e.g., a text field that has both a value and an error state), use a nested `sealed interface`. This keeps the reducer centralized and organized.
        ```kotlin
        sealed interface WeightInput : PartialStateChange {
            data class ValueChange(val value: String) : WeightInput
            data class Error(val message: StringResource?) : WeightInput

            override fun reduce(oldState: ViewState) = when (this) {
                is ValueChange -> oldState.copy(weight = value, weightError = null)
                is Error -> oldState.copy(weightError = message)
            }
        }
        ```

#### 3. Component Responsibilities
- **ViewModel**: Inherits from `BaseMviViewModel`. Processes `ViewIntent`, orchestrates UseCases, and updates state by applying `PartialStateChange` reducers.
- **Screen**: The "Glue". Uses the `MviScreen` wrapper to standardize state collection and `ViewEvent` handling. It links the ViewModel to the `Content`.
- **Content**: A stateless Composable. It receives the `ViewState` and an `onIntent` lambda. It is responsible for:
    - **Adaptive Layout**: Handling different screen sizes using `WindowSizeClass`.
    - **UI Composition**: Assembling the screen from internal `components` and `core-ui` widgets.

### Base Classes
- **ViewModel**: Must inherit from `BaseMviViewModel<ViewIntent, ViewState, ViewEvent>`.
- **Screen Wrapper**: Must use the `MviScreen` wrapper to handle lifecycle, state collection, and single event processing.

## Module Layers
- **Presentation**: UI and UX logic. **Rule**: ViewModels MUST NOT depend on Repositories directly. Always use UseCases to interact with the domain/data layer.
- **Domain**: Pure business logic and data models (POJOs).
- **Data**: Implementation of persistence, networking, and repositories.
- **Core**: Shared utilities, styling, and navigation base.

For coding standards (naming, wildcards, localization), see [CONTRIBUTING.md](CONTRIBUTING.md).
For AI-specific instructions, see [AGENTS.md](AGENTS.md).
