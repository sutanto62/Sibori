# Modularization Strategy (Now in Android aligned)

This document outlines a pragmatic modularization approach aligned with the Now in Android (NiA) Modularization Learning Journey.

## High-level module map

```
app/
core/
  designsystem/
  ui/
  common/
  model/
  testing/               # test fixtures/utilities (androidTest/test)
  network/               # when needed
  database/              # when needed
  datastore/             # when needed
  data/                  # repositories, composes data sources
  domain/                # use cases (optional)
feature/
  home/
  favorites/
  profile/
```

## Responsibilities
- **app**: Composition root, navigation host, DI entry points, app-level configuration.
- **core:designsystem**: Theme, colors, typography, reusable Compose components.
- **core:ui**: UI helpers (WindowSizeClass, preview utils), base UI primitives.
- **core:common**: Kotlin extensions, result/error types, coroutines utilities, time/format.
- **core:model**: Stable app-level models shared across features and data layer.
- **core:network/database/datastore**: Isolated data sources with their own APIs.
- **core:data**: Repository implementations (depends on network/database/datastore/model/common).
- **core:domain (optional)**: Use cases/interactors, business logic orchestrating repositories.
- **core:testing**: Fakes, test rules, Compose testing helpers for reuse.
- **feature:<x>**: Screen(s) + state + ViewModel; depends only on core modules.

## Dependency direction (enforced)
- `feature -> core:(designsystem|ui|common|model|domain)`
- `core:data -> core:(network|database|datastore|model|common)`
- `app -> feature:*` and selected `core:*`
- No feature?feature dependencies. Prefer `implementation` over `api` to minimize surface area and speed builds.

## Gradle settings includes
```kotlin
// settings.gradle.kts
include(
  ":app",
  ":core:designsystem", ":core:ui", ":core:common", ":core:model",
  ":feature:home", ":feature:favorites", ":feature:profile",
  // add later as needed
  // ":core:network", ":core:database", ":core:datastore", ":core:data", ":core:domain", ":core:testing"
)
```

## Base Android/Compose config (library modules)
```kotlin
plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
}

android {
  namespace = "id.or.sutanto.sibori.core.designsystem" // adjust per-module
  compileSdk = 36
  defaultConfig { minSdk = 26 }
  buildFeatures { compose = true }
  composeOptions { /* handled by BOM */ }
}

dependencies {
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.ui.tooling.preview)
  debugImplementation(libs.androidx.compose.ui.tooling)
}
```

## Feature module example
```kotlin
plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
}

android { namespace = "id.or.sutanto.sibori.feature.home" }

dependencies {
  implementation(project(":core:designsystem"))
  implementation(project(":core:ui"))
  implementation(project(":core:common"))
  implementation(project(":core:model"))

  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.material.icons.extended)
}
```

## Navigation
- Keep the navigation host/graph in `app`.
- Each feature exposes either a typed route contract (constants + arguments) or an `@Composable` entry.
- `app` orchestrates navigation; avoid feature?feature calls.

## DI (Hilt recommended)
- Use Hilt across modules; define `@Module` in data sources and repositories with appropriate `@InstallIn` scopes.
- Feature modules provide their own `ViewModel`s.
- Keep DI bindings near their modules to preserve boundaries.

## Testing
- Put shared test utilities in `core:testing` and depend on it from test source sets.
- Feature tests should not depend on other features; rely on fakes from `core:testing`.

## Build logic (optional but NiA-aligned)
- Add `build-logic/` with convention plugins (android-application, android-library, compose, testing) to centralize common config.
- Move common Android/Compose options into those plugins.

## Migration plan (pragmatic order)
1. Extract `core:designsystem` (move `Theme.kt`, `Color.kt`, `Type.kt`), point `app` to it.
2. Create `feature:home` and move `HomeScreen`.
3. Create `core:common` and `core:ui` and move emerging shared helpers/components.
4. Create `feature:favorites` and `feature:profile` modules (initial placeholders).
5. Introduce `core:model` for shared models used by multiple features/layers.
6. When adding real data: create `core:network`/`database`/`datastore`, then `core:data` (and `core:domain` if desired).
7. Add `core:testing` for reusable test fixtures and rules.
8. Optionally add `build-logic/` for convention plugins and enable per-module Compose configs.

## Rules of thumb
- Strict boundaries: features own UI/state; data concerns stay in core data sources/repositories.
- Stable APIs: expose minimal public surface; default to `internal`.
- Prefer `implementation` deps; use `api` only when modules must re-expose types.
- Keep modules buildable and testable in isolation.
