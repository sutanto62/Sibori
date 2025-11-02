package id.or.sutanto.sibori.core.designsystem.components

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import id.or.sutanto.sibori.core.designsystem.theme.SiboriTheme
import org.junit.Rule
import org.junit.Test

class ActionCircleTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun shows_label_and_is_clickable() {
        composeTestRule.setContent {
            SiboriTheme {
                val clicked = remember { mutableStateOf(false) }
                ActionCircle(label = "CS", onClick = { clicked.value = true })
            }
        }
        val node = composeTestRule.onNodeWithText("CS").assertIsDisplayed()
        node.assertWidthIsAtLeast(48.dp).assertHeightIsAtLeast(48.dp)
        node.performClick()
    }
}
