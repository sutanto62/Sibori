package id.or.sutanto.sibori.core.designsystem.components

import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import id.or.sutanto.sibori.core.designsystem.theme.SiboriTheme
import org.junit.Rule
import org.junit.Test

class SectionHeaderTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun title_is_displayed() {
        composeTestRule.setContent {
            SiboriTheme { SectionHeader(title = "Minta Tolong") }
        }
        composeTestRule.onNodeWithText("Minta Tolong").assertIsDisplayed()
    }

    @Test
    fun chevron_has_min_touch_target_when_action_provided() {
        composeTestRule.setContent {
            SiboriTheme { SectionHeader(title = "Title", onChevronClick = {}) }
        }
        composeTestRule
            .onNodeWithContentDescription("More")
            .assertIsDisplayed()
            .assertWidthIsAtLeast(48.dp)
            .assertHeightIsAtLeast(48.dp)
    }
}
