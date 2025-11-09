package id.or.sutanto.sibori.feature.home

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import id.or.sutanto.sibori.core.data.FakeHomeRepository
import id.or.sutanto.sibori.core.model.MinistryType
import id.or.sutanto.sibori.core.model.ReplacementStatus
import id.or.sutanto.sibori.core.model.WeekEmphasis
import id.or.sutanto.sibori.core.model.ConfirmationStatus

class HomeRepositoryTest {
    private lateinit var repository: FakeHomeRepository

    @Before
    fun setup() {
        repository = FakeHomeRepository()
    }

    @Test
    fun `getUserName returns expected name`() = runTest {
        val userName = repository.getUserName()
        assertEquals("Cayadi", userName)
    }

    @Test
    fun `getNextAssignment returns valid assignment`() = runTest {
        val assignment = repository.getNextAssignment()
        assertNotNull(assignment)
        
        assignment?.let {
            assertEquals("assign_1", it.id)
            assertEquals(MinistryType.PRIEST_ASSISTANT, it.ministryType)
            assertEquals("St. Peter Parish", it.location)
            assertEquals(false, it.isConfirmedByUser)
            assertEquals(ReplacementStatus.NONE, it.replacementStatus)
            assertTrue(it.startAt > System.currentTimeMillis())
        }
    }

    @Test
    fun `getWeekBadges returns expected badges`() = runTest {
        val badges = repository.getWeekBadges()
        
        assertEquals(4, badges.size)
        assertEquals("M1", badges[0].label)
        assertEquals(WeekEmphasis.Primary, badges[0].emphasis)
        assertEquals(ConfirmationStatus.Confirmed, badges[0].confirmation)
        
        assertEquals("M2", badges[1].label)
        assertEquals(WeekEmphasis.Neutral, badges[1].emphasis)
        assertEquals(ConfirmationStatus.Confirmed, badges[1].confirmation)
        
        assertEquals("S1", badges[2].label)
        assertEquals(WeekEmphasis.Neutral, badges[2].emphasis)
        assertEquals(ConfirmationStatus.Done, badges[2].confirmation)
        
        assertEquals("GM2", badges[3].label)
        assertEquals(WeekEmphasis.Neutral, badges[3].emphasis)
        assertEquals(ConfirmationStatus.Done, badges[3].confirmation)
    }

    @Test
    fun `getAnnouncements returns expected announcements`() = runTest {
        val announcements = repository.getAnnouncements()
        
        assertEquals(2, announcements.size)
        assertEquals("ann_1", announcements[0].id)
        assertEquals("Reminder: Rehearsal on Friday", announcements[0].title)
        assertEquals("Choir rehearsal at 7 PM in the hall", announcements[0].subtitle)
        
        assertEquals("ann_2", announcements[1].id)
        assertEquals("Schedule change protocol", announcements[1].title)
        assertEquals("Request replacements early to notify others", announcements[1].subtitle)
    }

    @Test
    fun `getOpenNeedsCount returns expected count`() = runTest {
        val count = repository.getOpenNeedsCount()
        assertEquals(3, count)
    }
}
