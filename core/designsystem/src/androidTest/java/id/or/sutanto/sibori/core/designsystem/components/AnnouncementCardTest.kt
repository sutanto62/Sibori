package id.or.sutanto.sibori.core.designsystem.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import id.or.sutanto.sibori.core.designsystem.theme.SiboriTheme
import org.junit.Rule
import org.junit.Test

class AnnouncementCardTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun title_and_subtitle_displayed() {
        composeTestRule.setContent {
            SiboriTheme { AnnouncementCard(title = "Title", subtitle = "Subtitle") }
        }
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Subtitle").assertIsDisplayed()
    }

    @Test
    fun chevron_click_target_minimum_when_clickable() {
        composeTestRule.setContent {
            SiboriTheme { AnnouncementCard(title = "T", subtitle = null, onChevronClick = {}) }
        }
        composeTestRule
            .onNodeWithContentDescription("More")
            .assertIsDisplayed()
            .assertWidthIsAtLeast(48.dp)
            .assertHeightIsAtLeast(48.dp)
    }
}
