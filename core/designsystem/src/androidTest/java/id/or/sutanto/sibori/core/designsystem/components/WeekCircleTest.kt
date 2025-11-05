package id.or.sutanto.sibori.core.designsystem.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import id.or.sutanto.sibori.core.designsystem.theme.SiboriTheme
import org.junit.Rule
import org.junit.Test

class WeekCircleTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun label_is_displayed() {
        composeTestRule.setContent {
            SiboriTheme { WeekCircle(label = "M1", emphasis = WeekCircleEmphasis.Primary, indicator = WeekCircleIndicator.Confirmed) }
        }
        composeTestRule.onNodeWithText("M1").assertIsDisplayed()
    }
}
