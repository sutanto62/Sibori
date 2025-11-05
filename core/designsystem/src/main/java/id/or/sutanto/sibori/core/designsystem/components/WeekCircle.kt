package id.or.sutanto.sibori.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.or.sutanto.sibori.core.designsystem.theme.SiboriTheme

enum class WeekCircleEmphasis { Primary, Neutral }

enum class WeekCircleIndicator { None, Confirmed, Done }

@Composable
fun WeekCircle(
    label: String,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    emphasis: WeekCircleEmphasis = WeekCircleEmphasis.Neutral,
    indicator: WeekCircleIndicator = WeekCircleIndicator.None,
) {
    val backgroundColor = when (emphasis) {
        WeekCircleEmphasis.Primary -> MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
        WeekCircleEmphasis.Neutral -> Color.Transparent
    }
    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)

    Box(modifier = modifier.size(size)) {
        // The circular chip background and label
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(backgroundColor)
                .border(width = 1.dp, color = borderColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = label, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
        }

        // Overlay indicator on top of the circle (can slightly protrude)
        when (indicator) {
            WeekCircleIndicator.Confirmed -> IndicatorDot(color = Color(0xFF2E7D32)) // green
            WeekCircleIndicator.Done -> IndicatorDot(color = MaterialTheme.colorScheme.onSurface) // black-ish
            WeekCircleIndicator.None -> Unit
        }
    }
}

@Composable
private fun BoxScope.IndicatorDot(color: Color) {
    Box(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .offset(y = (4).dp, x = (-4).dp)
            .size(10.dp)
            .size(10.dp)
            .clip(CircleShape)
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
private fun WeekCirclePreview() {
    SiboriTheme {
        WeekCircle(label = "M1", emphasis = WeekCircleEmphasis.Primary, indicator = WeekCircleIndicator.Confirmed)
    }
}
