---
description: Compose Guidelines 
globs:
alwaysApply: true 
---

- Use stateless composables with state hoisting; keep side-effects in APIs like `LaunchedEffect`, `remember`, `derivedStateOf`.
- Use theming from `app/src/main/java/id/or/sutanto/sibori/ui/theme/*`; extend via `Theme.kt` rather than duplicating tokens.
- Provide previews for new composables when feasible (no network/DB in previews).
- Prefer unidirectional data flow; avoid coupling composables to repositories directly.