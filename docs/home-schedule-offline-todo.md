## Home Schedule (This Week) Offline-First TODO

- **Scope & Contracts**
  - [x] Confirm `ScheduleEvent` shared model shape in `core:model` aligns with Supabase schema and Room entity needs (id/ministryId/assignedUserId/scheduledAt/status mirrors Supabase, fuels Room entity).
  - [x] Document `ThisWeek` filter rules (time zone, week start) inside `core:common` utilities (`ThisWeekWindow` in `core:common` pins Asia/Jakarta + Monday-start range helpers).
  - [ ] Define status enum + mapper to reconcile Supabase values, Room entity, and UI labels.

- **Local Persistence (`core:database`)**
  - [ ] Introduce Room entity + DAO for `ScheduleEventEntity` with paging + weekly query.
  - [ ] Add type converters for timestamp/status fields and write initial migration.
  - [ ] Decide cache expiry / eviction policy and expose as DAO helper.

- **Remote Data Source (`core:data`)**
  - [ ] Create Supabase service contract for `schedule_events` with DTO + serialization adapters.
  - [ ] Implement fetch for `ThisWeek` window using remote filters and fallback to local filtering.
  - [ ] Handle network errors with retry/backoff strategy and surfaced failure reasons.

- **Repository & Sync (`core:data`)**
  - [ ] Implement repository composing Room + Supabase with Flow stream exposing weekly events.
  - [ ] Add bidirectional sync policy: local pending actions win, remote overrides stale data.
  - [ ] Schedule background refresh trigger (manual + app foreground) and expose last sync timestamp.

- **Domain Layer (`core:domain`)**
  - [ ] Add `GetThisWeekScheduleUseCase` returning Flow with refresh trigger parameter.
  - [ ] Implement action use cases (`ConfirmScheduleEvent`, `RequestReplacement`) with optimistic updates and rollback on failure.
  - [ ] Define domain-level error types surfaced to UI (offline, auth, validation).

- **Feature: Home (`feature:home`)**
  - [ ] Extend ViewModel state holder to consume use cases, manage offline indicators, and collect sync status.
  - [ ] Update composables to display weekly list, loading placeholders, offline banners, and retry affordances.
  - [ ] Wire confirm / request replacement buttons to domain actions with user feedback cues.

- **Testing & Tooling**
  - [ ] Provide fakes for Room DAO + Supabase service in `core:testing`.
  - [ ] Cover repository + use cases with unit tests for cache, sync, and optimistic flows.
  - [ ] Add instrumentation test for offline interaction (toggle network, verify UI state).
  - [ ] Emit analytics (Statsig/PostHog) + performance (New Relic) events for fetch + actions.
