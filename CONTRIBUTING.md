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
- **Mappers**: All mappers MUST inherit from `dev.stukalo.mealplanner.core.common.mapper.BaseMapper<In, Out>` to ensure consistency in mapping individual objects and lists.
- **Clock**: Use `kotlin.time.Clock` from the Kotlin standard library for all time-related operations.
- **Injection**: Inject `Clock` via constructor in ViewModels, Repositories, or UseCases *only when time-related logic is required*. Avoid injecting it if it's not used.
- **Strict Prohibition**: NEVER use static time providers or direct imports of system time like `Clock.System`, `System.currentTimeMillis()`, or the deprecated `kotlinx.datetime.Clock.System`. Always prefer the injected instance to ensure testability.
- **Magic Numbers**: Avoid magic numbers. Use named constants (val/const) to explain the purpose of values.

## Localization
- **No Hardcoded Strings**: All user-facing text must use `Res.string` from the `:core-localization` module.
- **Multilingual Support**: When adding a new string resource to `values/strings.xml`, you MUST also add its translation to `values-uk/strings.xml`.
- **Non-Translatable Strings**: For strings that don't need translation (e.g., format patterns like `%1$s (%2$s)` or the app name), use the `translatable="false"` attribute in the XML.

## Coroutines & Dispatchers
This policy establishes standard practices for selecting and switching Kotlin Coroutine Dispatchers.

### Core Principles
| Layer / Component | Default Dispatcher | Thread Switching Responsibility |
| :--- | :--- | :--- |
| **UI / Presentation** (ViewModel, Flow Collection) | `Dispatchers.Main.immediate` | Initiates jobs and updates UI state directly. |
| **Data Layer** (Retrofit, Room, Ktor) | Underlying Library Managed | Main-safe by contract. No explicit switching needed. |
| **Heavy Computation / Disk I/O** (Custom) | `Dispatchers.Default` / `Dispatchers.IO` | Switches internally via `withContext` where the heavy work occurs. |

### Detailed Rules
#### 1. Keep the Presentation Layer on `Dispatchers.Main.immediate`
All UI interactions, intent processing, and ViewModel state updates should execute on the main thread.
- **Rule**: Use `viewModelScope.launch` without forcing `Dispatchers.IO` or `Dispatchers.Default`.
- **Why `immediate`?**: It executes the block synchronously if called from the main thread, avoiding frame-drop lags and race conditions during fast UI state mutations.

#### 2. Adhere to Main-Safety in Data Sources
Modern Kotlin-first libraries (Retrofit, Room, Ktor) are Main-Safe by implementation.
- **Rule**: Do not wrap suspending library calls in `withContext(Dispatchers.IO)`.
- **Why?**: These libraries utilize Non-Blocking Asynchronous I/O. Wrapping them in `Dispatchers.IO` causes two unnecessary context switches, wasting CPU cycles and introducing latency.

#### 3. Enforce In-Place Dispatcher Switching for Heavy Work
If a function performs CPU-bound work or blocking File/Database I/O, it must be responsible for its own thread switching.
- **Rule**: Hide dispatcher switching inside the implementation using `withContext(...)`. Callers should never need to know which thread a suspend function runs on.
- **Why?**: This preserves the Main-Safe contract, allowing ViewModels to invoke functions safely from the Main thread.

#### 4. Selection Criteria (`IO` vs `Default`)
- **`Dispatchers.IO` (Blocking I/O)**: Use for legacy blocking APIs (`java.io.File`, `BitmapFactory`, etc.). Scales up to 64 threads.
- **`Dispatchers.Default` (CPU-Bound)**: Use for complex algorithms, large collection processing, JSON parsing, crypto. Fixed to core count.

## Dependency Management
- **Koin**: Use Koin for Dependency Injection.
- **`singleOf` Rule**: Prefer using `singleOf(::Implementation) bind Interface::class` over `single { Implementation(get(), ...) } bind Interface::class` to reduce boilerplate.
- **Versions**: Define dependencies in `libs.versions.toml`.
