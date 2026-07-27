# Coding Standards & Conventions

These rules apply to all developers and AI assistants working on this project.

## General Rules
- **No Wildcard Imports**: Explicitly list all imports in the import section. NEVER use `import package.*`.
- **Imports vs FQN**: Always use the `import` section at the top of the file. Do not use Fully Qualified Names (e.g., `androidx.compose.ui.Modifier`) directly in the code unless there is a naming conflict.
- **One Class per File**: Keep every class, interface, or top-level object in its own separate file.
- **Organization**: If creating helper functions or utility classes, create a dedicated package to keep related code together.
- **KDoc**: Provide documentation for all public classes and complex functions.

## UI & Styling
- **Previews**: Every `@Composable` function MUST have a corresponding `@Preview` function (usually in the same file or a dedicated `preview` package) to ensure UI can be verified independently.
- **Theme**: Always use `dev.stukalo.mealplanner.presentation.core.styling.Theme` for colors, spacing, and typography.
- **Dimensions**: Use `Theme.spacing.spaceX` for margins and padding.
- **Components**: Check `:presentation-core-ui` for reusable widgets (Buttons, Cards, Inputs) before creating new ones.

## Localization
- **No Hardcoded Strings**: All user-facing text must use `Res.string` from the `:core-localization` module.

## Dependency Management
- Use Koin for Dependency Injection.
- Define dependencies in `libs.versions.toml`.
