package id.or.sutanto.sibori.core.domain

import id.or.sutanto.sibori.core.data.HomeRepository
import id.or.sutanto.sibori.core.model.Announcement
import id.or.sutanto.sibori.core.model.MinistryAssignment
import id.or.sutanto.sibori.core.model.WeekBadge

/**
 * Aggregated data needed for the Home screen.
 */
data class HomeData(
    val userName: String,
    val nextAssignment: MinistryAssignment?,
    val weekBadges: List<WeekBadge> = emptyList(),
    val announcements: List<Announcement> = emptyList(),
    val openNeedsCount: Int = 0,
)

/**
 * Aggregates data needed by Home screen.
 */
class GetHomeDataUseCase(
    private val repository: HomeRepository,
) {
    /**
     * Returns HomeData, or null if there's nothing meaningful to show.
     */
    suspend operator fun invoke(): HomeData? {
        val userName = repository.getUserName()
        val nextAssignment = repository.getNextAssignment()
        val weekBadges = repository.getWeekBadges()
        val announcements = repository.getAnnouncements()
        val openNeedsCount = repository.getOpenNeedsCount()

        val isEmpty = nextAssignment == null &&
            weekBadges.isEmpty() &&
            announcements.isEmpty() &&
            openNeedsCount == 0

        if (isEmpty) return null

        return HomeData(
            userName = userName,
            nextAssignment = nextAssignment,
            weekBadges = weekBadges,
            announcements = announcements,
            openNeedsCount = openNeedsCount,
        )
    }
}
