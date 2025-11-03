package id.or.sutanto.sibori.core.model

/**
 * Domain models used throughout the app.
 * These are stable, app-level models shared across features and data layer.
 */

// --- User Profile ---

data class Profile(
    val id: String,
    val name: String,
    val code: String? = null,
    val email: String? = null,
    val profileImagePath: String? = null,
    val phone: String? = null,
)

// --- Ministry Types ---

/** Type of ministry/service. Expand as we add more. */
enum class MinistryType {
    MASS,
    MASS_USHER,
    CHOIR,
}

// --- Assignment & Replacement Models ---

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

// --- Announcement ---

/** Simple announcement content to show on Home. */
data class Announcement(
    val id: String,
    val title: String,
    val subtitle: String,
)

// --- Week Badge Models ---

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
