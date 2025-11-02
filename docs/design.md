### **Design Document: Sibori App**

---

### **1. Overview**

This document outlines the technical design for the Sibori application. The design is based on the Product Requirements Document (`plan.md`) and aims to create a scalable and maintainable Android application using modern development practices.

The architecture will be client-server, with a Jetpack Compose-based Android app and a Supabase backend. Observability and product analytics will be handled by New Relic and Statsig/PostHog, respectively.

### **2. Architecture**

We will adopt a **Clean Architecture** for the Android application to separate concerns and improve testability.

*   **UI Layer:** Built with Jetpack Compose. This layer is responsible for displaying the application data and sending user events to the domain layer. It will contain Composables, ViewModels, and UI state holders.
*   **Domain Layer:** Contains the core business logic of the application in the form of Use Cases (e.g., `GetScheduleUseCase`, `RequestReplacementUseCase`). This layer is independent of the UI and data layers.
*   **Data Layer:** Responsible for providing data to the domain layer. The app will follow an offline-first architecture.
    *   **Single Source of Truth:** A local Room database will serve as the single source of truth for all application data displayed in the UI.
    *   **Data Synchronization:** The `Repository` layer will be responsible for fetching data from the remote Supabase backend and storing it in the local Room database. This keeps the local data fresh.
    *   **UI Reactivity:** The UI will observe data streams (Kotlin Flows) from the Room database. When the database is updated, the UI will automatically refresh. This ensures a responsive user experience, even when offline.
    *   **Remote Data Source:** A dedicated data source class will handle all communication with the Supabase API.
    *   **Local Data Source:** A set of DAOs (Data Access Objects) will provide an interface for interacting with the Room database.

**Backend and Third-Party Services:**

*   **Backend-as-a-Service (BaaS):** Supabase will be used for the database, user authentication, and serverless functions.
*   **Application Performance Monitoring (APM):** New Relic will be integrated to monitor app performance, crashes, and network requests.
*   **Product Analytics:** Statsig or PostHog will be used for A/B testing, feature flagging, and tracking user behavior to measure success metrics.

### **3. Data Models (Supabase Schema)**

We will create the following tables in the Supabase PostgreSQL database. Corresponding entity classes will be created for the local Room database to enable offline caching.

*   **`profiles`**: Extends the `auth.users` table to store public user information.
    *   `id` (uuid, foreign key to `auth.users.id`)
    *   `full_name` (text)
    *   `phone_number` (text)
    *   `role` (text, e.g., 'assistant', 'admin')
    *   `picture`

*   **`ministries`**: Stores the different types of ministries.
    *   `id` (bigint, primary key)
    *   `name` (text, e.g., 'Special Ordinary Minister')
    *   `description` (text)
    *   `code` (text)

*   **`schedule_events`**: The main table for all scheduled events.
    *   `id` (bigint, primary key)
    *   `ministry_id` (bigint, foreign key to `ministries.id`)
    *   `user_id` (uuid, foreign key to `profiles.id`)
    *   `event_timestamp` (timestampz)
    *   `status` (text, e.g., 'confirmed', 'pending_replacement', 'unconfirmed')

*   **`replacement_requests`**: Tracks requests for substitutes.
    *   `id` (bigint, primary key)
    *   `schedule_event_id` (bigint, foreign key to `schedule_events.id`)
    *   `requesting_user_id` (uuid, foreign key to `profiles.id`)
    *   `fulfilling_user_id` (uuid, nullable, foreign key to `profiles.id`)
    *   `status` (text, 'open', 'closed')

### **4. UI/UX and Screen Flow**

The app will be a single-activity application using a `NavigationSuiteScaffold` for top-level navigation, as already started in `MainActivity.kt`.

#### **Navigation**

I'll update the `AppDestinations` enum to better reflect the app's features from the PRD:

*   **My Schedule:** The main screen showing the user's personal upcoming assignments.
*   **Open Requests:** A list of available assignments that the user can volunteer for.
*   **Profile:** The user's profile screen.

#### **Screens**

1.  **Login/Onboarding Screen:** A simple screen for email/password login, leveraging Supabase Auth.
2.  **My Schedule Screen (`HomeScreen`):**
    *   Displays a list of `ScheduleEvent` items for the logged-in user.
    *   Each item will show the ministry, date, and time.
    *   Each item will have two buttons:
        *   **Confirm:** Changes the event status to 'confirmed' and notifies the admin (via a Supabase Function).
        *   **Request Replacement:** Creates a new entry in `replacement_requests` and changes the event status to 'pending_replacement'.
3.  **Open Requests Screen (`OpenRequestsScreen`):**
    *   Displays a list of `replacement_requests` where the status is 'open'.
    *   Each item will show ministry, date, and time.
    *   A user can tap "Accept" on an item, which will:
        1.  Update the `replacement_requests` status to 'closed'.
        2.  Update the `schedule_events` table, reassigning the `user_id` to the current user.
        3.  Notify the original user and admin.
4.  **Profile Screen (`ProfileScreen`):**
    *   Displays the user's `full_name` and `phone_number`.
    *   Includes a "Log Out" button.
