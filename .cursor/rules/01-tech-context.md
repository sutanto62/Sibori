---
description: Tech context for `SIBORI` app.
globs:
alwaysApply: false
---

- Language: Kotlin (JDK 11)
- UI: Jetpack Compose + Material 3
- Build: Gradle Kotlin DSL with Version Catalog (`gradle/libs.versions.toml`)
- App module: `app/` (single-module). Namespace: `id.or.sutanto.sibori`
- SDK: `minSdk 24`, `targetSdk 36`, `compileSdk release(36)`