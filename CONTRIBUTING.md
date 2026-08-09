# Coding Standards & Conventions

These rules apply to all developers and AI assistants working on this project.

## General Rules
- **No Wildcard Imports**: Explicitly list all imports in the import section. NEVER use `import package.*`.
- **Imports vs FQN**: Always use the `import` section at the top of the file. Do not use Fully Qualified Names (e.g., `androidx.compose.ui.Modifier`) directly in the code unless there is a naming conflict.
- **One Class per File**: Keep every class, interface, top-level object, or top-level @Composable function in its own separate file. Private helper functions or private Composables can remain in the same file as their parent.
- **Organization**: If creating helper functions or utility classes, create a dedicated package to keep related code together.
- **Clean Architecture**: Follow the interface-implementation split for UseCases. Put interfaces in `:domain-usecase` and implementations in `:domain-usecase-impl`.
- **KDoc**: Provide documentation for all public classes and complex functions.

## UI & Styling
- **Previews**: Every `@Composable` function MUST have a corresponding `@Preview` function (usually in the same file or a dedicated `preview` package) to ensure UI can be verified independently.
- **Theme**: Always use `dev.stukalo.mealplanner.presentation.core.styling.Theme` for colors, spacing, and typography.
- **Dimensions**: Use `Theme.spacing.spaceX` for margins and padding.
- **Components**: Check `:presentation-core-ui` for reusable widgets (Buttons, Cards, Inputs) before creating new ones.

## Logic & Data
- **Clock**: Use `kotlin.time.Clock` from the Kotlin standard library for all time-related operations.
- **Injection**: ALWAYS inject `Clock` via constructor in ViewModels, Repositories, or UseCases.
- **Strict Prohibition**: NEVER use static time providers like `Clock.System`, `System.currentTimeMillis()`, or the deprecated `kotlinx.datetime.Clock.System`.
- **Magic Numbers**: Avoid magic numbers. Use named constants (val/const) to explain the purpose of values.

## Localization
- **No Hardcoded Strings**: All user-facing text must use `Res.string` from the `:core-localization` module.
- **Multilingual Support**: When adding a new string resource to `values/strings.xml`, you MUST also add its translation to `values-uk/strings.xml`.
- **Non-Translatable Strings**: For strings that don't need translation (e.g., format patterns like `%1$s (%2$s)` or the app name), use the `translatable="false"` attribute in the XML.

## Dependency Management
- Use Koin for Dependency Injection.
- Define dependencies in `libs.versions.toml`.
