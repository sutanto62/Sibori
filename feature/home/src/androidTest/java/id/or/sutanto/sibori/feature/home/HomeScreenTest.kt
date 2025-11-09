package id.or.sutanto.sibori.feature.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.or.sutanto.sibori.core.designsystem.theme.SiboriTheme
import id.or.sutanto.sibori.core.data.HomeRepository
import id.or.sutanto.sibori.core.domain.GetHomeDataUseCase
import id.or.sutanto.sibori.core.domain.HomeData
import id.or.sutanto.sibori.core.model.Announcement
import id.or.sutanto.sibori.core.model.MinistryAssignment
import id.or.sutanto.sibori.core.model.MinistryType
import id.or.sutanto.sibori.core.model.ScheduleEventStatus
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun renders_ready_state_content() {
        val data = HomeData(
            userName = "Test",
            nextAssignment = MinistryAssignment(
                id = "a1",
                scheduleEventId = 1L,
                scheduledAt = 0L,
                ministryType = MinistryType.PRIEST_ASSISTANT,
                status = ScheduleEventStatus.UNCONFIRMED,
            ),
            weekBadges = emptyList(),
            announcements = listOf(Announcement("ann_1", "Reminder: Rehearsal on Friday", "Choir rehearsal at 7 PM in the hall")),
            openNeedsCount = 0
        )
        composeRule.setContent {
            SiboriTheme { HomeScreen(state = HomeUiState.Ready(data)) }
        }

        composeRule.onNodeWithText("Hai Test !").assertIsDisplayed()
        composeRule.onNodeWithText("Reminder: Rehearsal on Friday").assertIsDisplayed()
    }

    @Test
    fun renders_empty_state() {
        composeRule.setContent {
            SiboriTheme { HomeScreen(state = HomeUiState.Empty) }
        }
        composeRule.onNodeWithTag("home_empty").assertIsDisplayed()
    }

    @Test
    fun renders_error_state() {
        composeRule.setContent {
            SiboriTheme { HomeScreen(state = HomeUiState.Error("boom")) }
        }
        composeRule.onNodeWithTag("home_error").assertIsDisplayed()
    }

    @Test
    fun renders_loading_state() {
        composeRule.setContent {
            SiboriTheme { HomeScreen(state = HomeUiState.Loading) }
        }
        composeRule.onNodeWithTag("home_loading").assertIsDisplayed()
    }
}
