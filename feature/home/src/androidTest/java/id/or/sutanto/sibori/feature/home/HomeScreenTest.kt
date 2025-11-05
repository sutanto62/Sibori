package id.or.sutanto.sibori.feature.home

import androidx.compose.ui.test.assertIsDisplayed
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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun renders_ready_state_content() {
        val vm = HomeViewModel(GetHomeDataUseCase(FakeRepo(mode = Mode.DATA)))
        composeRule.setContent {
            SiboriTheme { HomeScreen(viewModel = vm) }
        }

        composeRule.onNodeWithText("Hai Test !").assertIsDisplayed()
        composeRule.onNodeWithText("Reminder: Rehearsal on Friday").assertIsDisplayed()
    }

    @Test
    fun renders_empty_state() {
        val vm = HomeViewModel(GetHomeDataUseCase(FakeRepo(mode = Mode.EMPTY)))
        composeRule.setContent {
            SiboriTheme { HomeScreen(viewModel = vm) }
        }
        composeRule.onNodeWithText("No data available").assertIsDisplayed()
    }

    @Test
    fun renders_error_state() {
        val vm = HomeViewModel(GetHomeDataUseCase(FakeRepo(mode = Mode.ERROR)))
        composeRule.setContent {
            SiboriTheme { HomeScreen(viewModel = vm) }
        }
        composeRule.onNodeWithText("Error: boom").assertIsDisplayed()
    }

    private enum class Mode { DATA, EMPTY, ERROR }

    private inner class FakeRepo(private val mode: Mode) : HomeRepository {
        override suspend fun getUserName(): String = "Test"
        override suspend fun getNextAssignment(): id.or.sutanto.sibori.core.model.MinistryAssignment? =
            when (mode) {
                Mode.DATA -> id.or.sutanto.sibori.core.model.MinistryAssignment(
                    id = "a1", startAt = 0L, ministryType = id.or.sutanto.sibori.core.model.MinistryType.MASS
                )
                Mode.EMPTY, Mode.ERROR -> null
            }
        override suspend fun getWeekBadges(): List<id.or.sutanto.sibori.core.model.WeekBadge> = emptyList()
        override suspend fun getAnnouncements(): List<id.or.sutanto.sibori.core.model.Announcement> =
            if (mode == Mode.DATA) listOf(Announcement("ann_1", "Reminder: Rehearsal on Friday", "Choir rehearsal at 7 PM in the hall")) else emptyList()
        override suspend fun getOpenNeedsCount(): Int = 0
    }
}
