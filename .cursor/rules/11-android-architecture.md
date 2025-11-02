---
description: Architecture 
globs:
alwaysApply: true
---

### Architecture & Modules
- **Layered, modular architecture**: `app` (entry), `feature/*` (UI + VM), `core/*` (shared), and data/infra modules as needed.
- **Feature isolation**: Each feature owns its UI, `ViewModel`, navigation surface, and DI bindings. Avoid cross-feature coupling.
- **Stable cores**: Keep `core/*` small, stable, and UI-agnostic (design system, analytics, network, database, util).
- **Strict dependency direction**: UI ? domain/use-cases ? repositories ? data sources. No upward or cyclic deps.

### UI Layer (Compose)
- **Unidirectional data flow (UDF)**: Composables are stateless; drive UI from `UiState` only.
- **State hoisting**: Business logic stays in `ViewModel`. Composables emit user events; `ViewModel` updates state.
- **Previewable UI**: Prefer parameterized, pure Composables; keep I/O out of UI.
- **Theming**: Use `core:designsystem` tokens/components consistently.

### ViewModel & State
- **Expose immutable state**: `StateFlow<UiState>` or `Immutable` snapshot states; never expose mutable state.
- **Clear UI states**: Model `Loading`, `Error`, `Empty`, and `Ready` explicitly.
- **Side-effects**: Encapsulate one-off events via channels or event-state; keep them distinct from render state.
- **Work off main thread**: Use coroutines; avoid blocking calls in `ViewModel`.

### Domain / Use Cases
- **Use cases when logic warrants**: Introduce use cases to compose multiple repositories or to encapsulate business rules.
- **Keep them small and testable**: Pure functions where possible; inject dependencies.
- **Don?t over-abstract**: If a `ViewModel` calls a single repo method with trivial mapping, a use case may be unnecessary.

### Data Layer
- **Repositories as single source of truth**: Repos mediate network, cache, and DB; UI never talks to data sources directly.
- **Model boundaries**: Keep DTOs (network), Entities (DB), and Domain models separate; add mappers at the boundaries.
- **Offline-first**: Prefer emitting cached/DB data immediately and sync in the background.
- **Flows over callbacks**: Expose `Flow`/suspend APIs; ensure cold, replay, or caching semantics are intentional.
- **Errors as data**: Convert exceptions to typed results or error states before reaching `ViewModel`.

### Dependency Injection
- **Hilt (or DI of choice)**: Provide bindings per module; avoid a global service locator.
- **Module boundaries**: Bind only what a module owns. Don?t leak internal implementations across boundaries.

### Navigation
- **Single-activity** with feature destinations. Keep route definitions near the feature.
- **Type-safe arguments**: Centralize navigation args; avoid stringly-typed routes scattered across code.

### Errors, Loading, Empty
- **Always renderable**: Every screen should gracefully represent loading, error, and empty states.
- **User feedback**: Prefer inline messages and retry actions; avoid silent failures.

### Testing
- **Unit-test critical logic**: ViewModels, use cases, repositories, and mappers.
- **Use fakes over mocks** where practical; test flows with Turbine.
- **UI tests**: Compose testing for states, semantics, and interactions.

### Gradle & Dependencies
- **Version catalog only**: Add libraries to `gradle/libs.versions.toml` and reference via `libs.*` aliases.
- **Keep modules lean**: Only depend on what you use; watch transitive bloat.

### Code Style
- **Clean boundaries**: No leaking of DTO/Entity types into UI.
- **Naming**: Clear, descriptive names; avoid abbreviations; prefer readability over cleverness.
- **Mappers**: Centralize and test; keep them simple and deterministic.
- **Coroutines**: Use structured concurrency; scope jobs to lifecycle owners.

### Performance & Quality
- **Avoid over-recomposition**: Keep state minimal and localized; use `remember` and `derivedStateOf` judiciously.
- **Immutability**: Prefer immutable models for UI state.
- **Tracing/metrics**: Add lightweight logging/metrics at boundaries when needed.

### Internationalization & Accessibility
- **Strings from resources**; avoid hard-coded text.
- **Semantics**: Provide content descriptions; ensure contrast and touch targets.

### Git & Reviews
- **Small, focused commits** with clear messages about intent (the ?why?).
- **Module-local changes**: Keep edits within a module when possible; refactor shared code into `core/*` deliberately.

### How to add a new feature
1. Create `feature/<name>` module with Composables, `ViewModel`, and navigation entry.
2. Define `UiState` and events first; build UI against fakes.
3. Add use case(s) if business logic composes multiple sources.
4. Implement repository and data sources; map models across layers.
5. Wire DI and navigation; add tests.

These rules summarize the Now in Android architecture approach, adapted to Sibori?s structure. Prefer simplicity first; introduce abstractions only when they reduce duplication or clarify intent.
