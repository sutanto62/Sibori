package id.or.sutanto.sibori.core.data

import id.or.sutanto.sibori.core.model.Announcement
import id.or.sutanto.sibori.core.model.MinistryAssignment
import id.or.sutanto.sibori.core.model.WeekBadge

/**
 * Home repository contract. In MVP we keep it simple and synchronous via suspend
 * functions. A use case will aggregate these into HomeData for the UI.
 */
interface HomeRepository {
    suspend fun getUserName(): String
    suspend fun getNextAssignment(): MinistryAssignment?
    suspend fun getWeekBadges(): List<WeekBadge>
    suspend fun getAnnouncements(): List<Announcement>
    suspend fun getOpenNeedsCount(): Int
}

/**
 * Local fake implementation to unblock UI and ViewModel.
 */
class FakeHomeRepository : HomeRepository {
    override suspend fun getUserName(): String = "Cayadi"

    override suspend fun getNextAssignment(): MinistryAssignment? =
        MinistryAssignment(
            id = "assign_1",
            startAt = sampleEpochMillis(dayOffset = 2, hour24 = 9),
            ministryType = id.or.sutanto.sibori.core.model.MinistryType.MASS,
            location = "St. Peter Parish",
            isConfirmedByUser = false,
            replacementStatus = id.or.sutanto.sibori.core.model.ReplacementStatus.NONE,
        )

    override suspend fun getWeekBadges(): List<WeekBadge> = listOf(
        WeekBadge(
            label = "M1",
            emphasis = id.or.sutanto.sibori.core.model.WeekEmphasis.Primary,
            indicator = id.or.sutanto.sibori.core.model.WeekIndicator.Black
        ),
        WeekBadge(
            label = "M2",
            emphasis = id.or.sutanto.sibori.core.model.WeekEmphasis.Neutral,
            indicator = id.or.sutanto.sibori.core.model.WeekIndicator.Black
        ),
        WeekBadge(
            label = "C",
            emphasis = id.or.sutanto.sibori.core.model.WeekEmphasis.Neutral,
            indicator = id.or.sutanto.sibori.core.model.WeekIndicator.Gray
        ),
        WeekBadge(
            label = "U",
            emphasis = id.or.sutanto.sibori.core.model.WeekEmphasis.Neutral,
            indicator = id.or.sutanto.sibori.core.model.WeekIndicator.Gray
        ),
    )

    override suspend fun getAnnouncements(): List<Announcement> = listOf(
        Announcement(
            id = "ann_1",
            title = "Reminder: Rehearsal on Friday",
            subtitle = "Choir rehearsal at 7 PM in the hall"
        ),
        Announcement(
            id = "ann_2",
            title = "Schedule change protocol",
            subtitle = "Request replacements early to notify others"
        ),
    )

    override suspend fun getOpenNeedsCount(): Int = 3
}

private fun sampleEpochMillis(dayOffset: Int, hour24: Int): Long {
    val now = System.currentTimeMillis()
    val oneDayMillis = 24L * 60 * 60 * 1000
    val base = now + (dayOffset * oneDayMillis)
    // Roughly snap to provided hour within that day without worrying about TZ/edge cases
    val millisInDay = base % oneDayMillis
    val targetMillisInDay = hour24 * 60L * 60 * 1000
    return base - millisInDay + targetMillisInDay
}
