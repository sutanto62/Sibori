package id.or.sutanto.sibori.core.common

import java.time.Clock
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters

/**
 * Canonical rules for the "This Week" schedule window used across the app.
 *
 * How we scope the window:
 * - Time zone: Asia/Jakarta, matching the parish's calendar and Supabase timestamps.
 * - Week start: Monday 00:00 local time.
 * - Week end: Sunday 23:59:59.999 local time (represented as the exclusive end instant).
 *
 * Prefer working with the provided helpers so Room queries, domain filters, and Supabase requests
 * stay consistent when we expand offline support.
 */
object ThisWeekWindow {
    val DEFAULT_ZONE: ZoneId = ZoneId.of("Asia/Jakarta")

    /**
     * Returns the inclusive start of the active "This Week" window as an [Instant].
     */
    fun startInstant(clock: Clock = Clock.system(DEFAULT_ZONE), zoneId: ZoneId = DEFAULT_ZONE): Instant {
        val today = LocalDate.now(clock.withZone(zoneId))
        val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        return weekStart.atStartOfDay(zoneId).toInstant()
    }

    /**
     * Returns the exclusive end of the active "This Week" window as an [Instant].
     * Consumers should treat the end as [start, end) in queries.
     */
    fun endInstant(clock: Clock = Clock.system(DEFAULT_ZONE), zoneId: ZoneId = DEFAULT_ZONE): Instant {
        val today = LocalDate.now(clock.withZone(zoneId))
        val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val weekEnd = weekStart.plusDays(7) // exclusive: following Monday start
        return weekEnd.atStartOfDay(zoneId).toInstant()
    }

    /**
     * Convenience range helper for filtering epoch millis collections.
     */
    fun currentMillisRange(clock: Clock = Clock.system(DEFAULT_ZONE), zoneId: ZoneId = DEFAULT_ZONE): LongRange {
        val start = startInstant(clock, zoneId).toEpochMilli()
        val endExclusive = endInstant(clock, zoneId).toEpochMilli()
        return start until endExclusive
    }

    /**
     * Expands a supplied [Instant] into the canonical start/end instants.
     * Useful when syncing batches from Supabase with a specific reference time.
     */
    fun forInstant(
        instant: Instant,
        zoneId: ZoneId = DEFAULT_ZONE,
    ): Pair<Instant, Instant> {
        val zoned = ZonedDateTime.ofInstant(instant, zoneId)
        val weekStart = zoned.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate()
        val startInstant = weekStart.atStartOfDay(zoneId).toInstant()
        val endInstant = weekStart.plusDays(7).atStartOfDay(zoneId).toInstant()
        return startInstant to endInstant
    }
}
