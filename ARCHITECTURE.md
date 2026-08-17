# Project Architecture

This document describes the high-level design and architectural patterns used in the MealPlanner project.

## High-Level Architecture
The project follows a **Modular Clean Architecture** with **MVI (Model-View-Intent)** in the presentation layer.

### Presentation Layer (MVI)
All feature modules MUST follow the MVI pattern and be structured as follows:

1.  **Navigation Graph**: The entry point. Uses `composable<NavigationDirection.Feature>` to link a route to a `Screen`.
2.  **Screen (The Logic Holder)**: 
    - Named `[Feature]Screen`.
    - Responsible for: Collecting State, Handling Single Events, Setting up ViewModel (via Koin), and logic like `LaunchedEffect`.
    - MUST NOT contain complex UI rendering.
3.  **Content (The UI Renderer)**:
    - Named `[Feature]Content`.
    - A stateless Composable that receives `ViewState` and an `onIntent` lambda.
4.  **Components**: Small, reusable UI elements within the feature.

### Base Classes
- **ViewModel**: Must inherit from `BaseMviViewModel<Intent, ViewState, SingleEvent>`.
- **Screen Wrapper**: Must use the `MviScreen` wrapper to standardize state collection and event handling.

## Module Layers
- **Presentation**: UI and UX logic. **Rule**: ViewModels MUST NOT depend on Repositories directly. Always use UseCases to interact with the domain/data layer.
- **Domain**: Pure business logic and data models (POJOs).
- **Data**: Implementation of persistence, networking, and repositories.
- **Core**: Shared utilities, styling, and navigation base.

For coding standards (naming, wildcards, localization), see [CONTRIBUTING.md](CONTRIBUTING.md).
For AI-specific instructions, see [AGENTS.md](AGENTS.md).
