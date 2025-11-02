package id.or.sutanto.sibori.feature.home

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
