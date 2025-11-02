package id.or.sutanto.sibori.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import id.or.sutanto.sibori.core.designsystem.components.SectionHeader
import id.or.sutanto.sibori.core.designsystem.components.WeekCircleItem
import id.or.sutanto.sibori.core.designsystem.components.WeekCircleEmphasis
import id.or.sutanto.sibori.core.designsystem.components.WeekCircleIndicator
import id.or.sutanto.sibori.core.designsystem.theme.SiboriTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        GreetingHeader(userName = "Cayadi")

        Spacer(Modifier.height(16.dp))

        ThisWeekCard(
            items = listOf(
                ThisWeekItem(label = "M1", emphasis = WeekCircleEmphasis.Primary, indicator = WeekCircleIndicator.Black),
                ThisWeekItem(label = "M2", emphasis = WeekCircleEmphasis.Neutral, indicator = WeekCircleIndicator.Black),
                ThisWeekItem(label = "P", emphasis = WeekCircleEmphasis.Neutral, indicator = WeekCircleIndicator.Grey),
                ThisWeekItem(label = "H1", emphasis = WeekCircleEmphasis.Neutral, indicator = WeekCircleIndicator.Grey)
            )
        )

        Spacer(Modifier.height(16.dp))

        AnnouncementCard(
            title = "Pengumuman tugas atau catatan",
            subtitle = "tentang protokol pelaksanaan tugas"
        )

        Spacer(Modifier.height(24.dp))

        HelpSection(
            onAddClick = { /* TODO: hook action */ },
            actions = listOf("CS", "BS")
        )
    }
}

@Composable
private fun GreetingHeader(userName: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Hai $userName !",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Ini adalah aplikasi untuk Prodi",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private data class ThisWeekItem(
    val label: String,
    val emphasis: WeekCircleEmphasis = WeekCircleEmphasis.Neutral,
    val indicator: WeekCircleIndicator = WeekCircleIndicator.None,
)

@Composable
private fun ThisWeekCard(items: List<ThisWeekItem>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Minggu Ini",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(12.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(items.size) { index ->
                    val item = items[index]
                    WeekCircleItem(
                        label = item.label,
                        emphasis = item.emphasis,
                        indicator = item.indicator
                    )
                }
            }
        }
    }
}

@Composable
private fun AnnouncementCard(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Leading circle placeholder
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                tonalElevation = 0.dp,
                color = Color.Transparent,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
                )
            ) {}

            Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                Text(text = title, style = MaterialTheme.typography.titleSmall)
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun HelpSection(onAddClick: () -> Unit, actions: List<String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        SectionHeader(title = "Minta Tolong")

        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            actions.forEach { label ->
                ActionCircle(label = label)
            }
        }

        Spacer(Modifier.height(8.dp))
        Divider()
    }
}

@Composable
private fun ActionCircle(label: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.size(84.dp),
        shape = CircleShape,
        tonalElevation = 1.dp,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
        )
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = label, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@PreviewFontScale
@Composable
private fun HomeScreenPreview() {
    SiboriTheme {
        HomeScreen()
    }
}
