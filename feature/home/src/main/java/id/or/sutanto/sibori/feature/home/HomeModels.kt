package id.or.sutanto.sibori.feature.home

/**
 * Core models needed for Home screen data and state.
 * These are UI-facing models kept inside the feature module for now.
 */

// --- Domain-ish models used by Home ---

data class Profile(
    val id: String,
    val name: String,
    val code: String? = null,
    val email: String? = null,
    val profileImagePath: String? = null,
    val phone: String? = null,
)

/** Type of ministry/service. Expand as we add more. */
enum class MinistryType {
    MASS,
    MASS_USHER,
    CHOIR,
}

/** Status related to replacement workflow for an assignment. */
enum class ReplacementStatus {
    NONE,          // No replacement requested
    OPEN,          // Replacement requested, waiting for acceptor
    ACCEPTED,      // Replacement accepted by another assistant
}

/** A single ministry assignment (personal or parish schedule item). */
data class MinistryAssignment(
    val id: String,
    val startAt: Long,
    val ministryType: MinistryType,
    val location: String? = null,
    val isConfirmedByUser: Boolean = false,
    val replacementStatus: ReplacementStatus = ReplacementStatus.NONE,
)

/** A replacement request for an existing assignment. */
enum class ReplacementRequestStatus { OPEN, ACCEPTED, CANCELED }

data class ReplacementRequest(
    val id: String,
    val assignmentId: String,
    val requestedByUserId: String,
    val acceptedByUserId: String? = null,
    val status: ReplacementRequestStatus = ReplacementRequestStatus.OPEN,
)

/** Simple announcement content to show on Home. */
data class Announcement(
    val id: String,
    val title: String,
    val subtitle: String,
)

// --- Home-specific presentation models ---

/**
 * Badge-like weekly summary item (mapped to WeekCircle in UI).
 * Keep presentation-agnostic; UI will map to design system specifics.
 */
enum class WeekEmphasis { Neutral, Primary }
enum class WeekIndicator { None, Gray, Black }

data class WeekBadge(
    val label: String,
    val emphasis: WeekEmphasis = WeekEmphasis.Neutral,
    val indicator: WeekIndicator = WeekIndicator.None,
)

/** Aggregated data needed for the Home content state. */
data class HomeData(
    val userName: String,
    val nextAssignment: MinistryAssignment?,
    val weekBadges: List<WeekBadge> = emptyList(),
    val announcements: List<Announcement> = emptyList(),
    val openNeedsCount: Int = 0,
)

// --- Home UI state ---

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
    data object Empty : HomeUiState
    data class Ready(val data: HomeData) : HomeUiState
}
