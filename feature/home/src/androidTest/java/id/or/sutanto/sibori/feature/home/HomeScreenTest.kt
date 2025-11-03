package id.or.sutanto.sibori.feature.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.onNode
import androidx.compose.ui.test.assertExists
import id.or.sutanto.sibori.core.designsystem.theme.SiboriTheme
import id.or.sutanto.sibori.core.data.FakeHomeRepository
import id.or.sutanto.sibori.core.data.HomeRepository
import id.or.sutanto.sibori.core.domain.GetHomeDataUseCase
import id.or.sutanto.sibori.core.model.Announcement
import id.or.sutanto.sibori.core.model.MinistryAssignment
import id.or.sutanto.sibori.core.model.WeekBadge
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_loadingState_displaysLoadingText() {
        val slowRepository = object : HomeRepository {
            override suspend fun getUserName(): String {
                kotlinx.coroutines.delay(1000)
                return "Test User"
            }
            override suspend fun getNextAssignment(): MinistryAssignment? = null
            override suspend fun getWeekBadges(): List<WeekBadge> = emptyList()
            override suspend fun getAnnouncements(): List<Announcement> = emptyList()
            override suspend fun getOpenNeedsCount(): Int = 0
        }
        
        val viewModel = HomeViewModel(GetHomeDataUseCase(slowRepository))
        
        composeTestRule.setContent {
            SiboriTheme {
                HomeScreen(viewModel = viewModel)
            }
        }

        // Initially should show loading
        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
    }

    @Test
    fun homeScreen_readyState_displaysContent() {
        val viewModel = HomeViewModel(GetHomeDataUseCase(FakeHomeRepository()))
        
        composeTestRule.setContent {
            SiboriTheme {
                HomeScreen(viewModel = viewModel)
            }
        }

        // Wait for data to load
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodes(hasText("Cayadi", substring = true))
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Should display greeting with user name
        composeTestRule.onNode(hasText("Cayadi", substring = true)).assertExists()
        
        // Should display week badges section
        composeTestRule.onNode(hasText("This Week", substring = true)).assertExists()
        
        // Should display announcements or help section
        composeTestRule.onNode(hasText("Help", substring = true)).assertExists()
    }

    @Test
    fun homeScreen_errorState_displaysErrorAndRetry() {
        val errorMessage = "Network error occurred"
        val errorRepository = object : HomeRepository {
            override suspend fun getUserName(): String {
                throw RuntimeException(errorMessage)
            }
            override suspend fun getNextAssignment(): MinistryAssignment? = null
            override suspend fun getWeekBadges(): List<WeekBadge> = emptyList()
            override suspend fun getAnnouncements(): List<Announcement> = emptyList()
            override suspend fun getOpenNeedsCount(): Int = 0
        }
        
        val viewModel = HomeViewModel(GetHomeDataUseCase(errorRepository))
        
        composeTestRule.setContent {
            SiboriTheme {
                HomeScreen(viewModel = viewModel)
            }
        }

        // Wait for error state
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodes(hasText("Error", substring = true))
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Should display error message
        composeTestRule.onNode(hasText(errorMessage, substring = true)).assertExists()
        
        // Should display retry button
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun homeScreen_errorState_retryButtonRetries() {
        var callCount = 0
        val errorMessage = "Network error"
        val retryRepository = object : HomeRepository {
            override suspend fun getUserName(): String {
                callCount++
                if (callCount == 1) {
                    throw RuntimeException(errorMessage)
                }
                return "Test User"
            }
            override suspend fun getNextAssignment(): MinistryAssignment? = null
            override suspend fun getWeekBadges(): List<WeekBadge> = emptyList()
            override suspend fun getAnnouncements(): List<Announcement> = emptyList()
            override suspend fun getOpenNeedsCount(): Int = 0
        }
        
        val viewModel = HomeViewModel(GetHomeDataUseCase(retryRepository))
        
        composeTestRule.setContent {
            SiboriTheme {
                HomeScreen(viewModel = viewModel)
            }
        }

        // Wait for error state
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodes(hasText("Error", substring = true))
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Click retry button
        composeTestRule.onNodeWithText("Retry").performClick()

        // Should eventually show content after retry succeeds
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodes(hasText("Test User", substring = true))
                .fetchSemanticsNodes().isNotEmpty() ||
            composeTestRule.onAllNodes(hasText("Loading", substring = true))
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun homeScreen_emptyState_displaysEmptyMessage() {
        val emptyRepository = object : HomeRepository {
            override suspend fun getUserName(): String = "Test User"
            override suspend fun getNextAssignment(): MinistryAssignment? = null
            override suspend fun getWeekBadges(): List<WeekBadge> = emptyList()
            override suspend fun getAnnouncements(): List<Announcement> = emptyList()
            override suspend fun getOpenNeedsCount(): Int = 0
        }
        
        val viewModel = HomeViewModel(GetHomeDataUseCase(emptyRepository))
        
        composeTestRule.setContent {
            SiboriTheme {
                HomeScreen(viewModel = viewModel)
            }
        }

        // Wait for empty state (use case returns null when all data is empty)
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodes(hasText("No data available", substring = true))
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Should display empty state message
        composeTestRule.onNodeWithText("No data available").assertIsDisplayed()
    }
}
