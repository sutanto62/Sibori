---
description: Modularization Strategy
globs:
alwaysApply: true
---

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
