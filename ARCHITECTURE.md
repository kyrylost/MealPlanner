# Project Architecture

This document describes the high-level design and architectural patterns used in the MealPlanner project.

## High-Level Architecture
The project follows a **Modular Clean Architecture** with **MVI (Model-View-Intent)** in the presentation layer.

### Presentation Layer (MVI)
All feature modules MUST follow a strict package structure. Deviation is prohibited.

#### 1. Mandatory Package Structure
```text
/src/commonMain/kotlin/.../feature/[name]/
├── di/                     # Koin module definition
├── navigation/            # Navigation graph registration
├── core/                  # Internal logic and feature-specific models
│   ├── model/             # All UI models and data classes
│   └── mapper/            # Feature-specific mappers
├── component/             # Reusable UI widgets for this feature only
└── screen/                # MVI Components
    ├── contract/          # ViewState, ViewIntent, ViewEvent, PartialStateChange
    ├── [Name]Screen.kt    # Logic holder & entry point
    ├── [Name]Content.kt   # Stateless UI renderer
    └── [Name]ViewModel.kt # Business logic
```

**CRITICAL RULE**: Do NOT create sub-packages inside `screen/` (e.g., `screen/model/` is FORBIDDEN). All models must reside in `core/model/`.

#### 2. Visibility Modifiers
To maintain strict encapsulation, almost everything within a feature module MUST be marked as `internal`.
- **`internal`**: ViewModels, Screens, Contents, MVI contracts, Models, Mappers, and Components.
- **`public`**: Only the Navigation Graph (usually in `navigation/`) and the Koin Module (usually in `di/`) are public.

#### 3. MVI Contract (`screen/contract/`)
- `ViewState`: Immutable data class representing the entire UI state.
- `ViewIntent`: Sealed interface for user actions or system triggers.
- `ViewEvent`: Sealed interface for one-time events (navigation, snackbars).
- **`PartialStateChange`**: A mandatory sealed interface (or class) inheriting from `MviPartialStateChange<ViewState>`. It encapsulates how specific actions update the `ViewState`.
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
- **ViewModel**: Inherits from `BaseMviViewModel`. Processes `ViewIntent`, orchestrates UseCases, and updates state EXCLUSIVELY by applying `PartialStateChange` reducers via `reduce(change)`. Directly modifying state or using lambdas in the ViewModel is strictly prohibited to keep business logic in the contract reducers.
- **Screen**: The "Glue". Uses the `MviScreen` wrapper to standardize state collection and `ViewEvent` handling. It links the ViewModel to the `Content`.
- **Content**: A stateless Composable. It receives the `ViewState` and an `onIntent` lambda. It is responsible for:
    - **Adaptive Layout**: Handling different screen sizes using `WindowSizeClass`.
    - **UI Composition**: Assembling the screen from internal `components` and `core-ui` widgets.

### Base Classes
- **ViewModel**: Must inherit from `BaseMviViewModel<ViewIntent, ViewState, ViewEvent>`. Use `sendEvent(ViewEvent)` to trigger one-time actions. These are automatically wrapped in `MviSideEffect.Feature`.
- **Screen Wrapper**: Must use the `MviScreen` wrapper. It automatically handles `MviSideEffect.System` (like `ShowSnackbar`) and provides the unwrapped `ViewEvent` to the screen's `onSingleEvent` callback.

## Module Layers
- **Presentation**: UI and UX logic. **Rule**: ViewModels MUST NOT depend on Repositories directly. Always use UseCases to interact with the domain/data layer.
- **Domain**: Pure business logic and data models (POJOs).
- **Data**: Implementation of persistence, networking, and repositories.
- **Core**: Shared utilities, styling, and navigation base.

For coding standards (naming, wildcards, localization), see [CONTRIBUTING.md](CONTRIBUTING.md).
For AI-specific instructions, see [AGENTS.md](AGENTS.md).
