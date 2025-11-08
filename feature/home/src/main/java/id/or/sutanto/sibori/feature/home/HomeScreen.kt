package id.or.sutanto.sibori.feature.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.platform.testTag
import id.or.sutanto.sibori.core.designsystem.components.SectionHeader
import id.or.sutanto.sibori.core.designsystem.components.WeekCircleEmphasis
import id.or.sutanto.sibori.core.designsystem.components.WeekCircleIndicator
import id.or.sutanto.sibori.feature.home.components.ThisWeekCard
import id.or.sutanto.sibori.core.designsystem.components.AnnouncementCard
import id.or.sutanto.sibori.core.designsystem.components.ActionCircle
import id.or.sutanto.sibori.core.designsystem.theme.SiboriTheme
import id.or.sutanto.sibori.core.domain.HomeData
import id.or.sutanto.sibori.core.model.WeekEmphasis
import id.or.sutanto.sibori.feature.home.HomeUiState

@Composable
fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    widthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    onRetry: () -> Unit = {},
) {
    when (val current = state) {
        is HomeUiState.Loading -> {
            LoadingState(modifier = modifier.statusBarsPadding())
        }
        is HomeUiState.Error -> {
            ErrorState(
                message = current.message,
                onRetry = onRetry,
                modifier = modifier.statusBarsPadding()
            )
        }
        is HomeUiState.Empty -> {
            EmptyState(modifier = modifier.statusBarsPadding())
        }
        is HomeUiState.Ready -> {
            HomeContent(
                data = current.data,
                widthSizeClass = widthSizeClass,
                modifier = modifier.statusBarsPadding()
            )
        }
    }
}

@Composable
private fun HomeContent(
    data: HomeData,
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
        GreetingHeader(userName = data.userName)

        Spacer(Modifier.height(16.dp))

        // Map WeekBadge to ThisWeekItem for UI
        val weekItems = data.weekBadges.toThisWeekItems()

        // Get first announcement or use default
        val announcement = data.announcements.firstOrNull()

        if (widthSizeClass >= WindowWidthSizeClass.Medium || isLandscape) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                ThisWeekCard(
                    items = weekItems,
                    modifier = Modifier.weight(1f)
                )
                if (announcement != null) {
                    AnnouncementCard(
                        title = announcement.title,
                        subtitle = announcement.subtitle,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        } else {
            ThisWeekCard(items = weekItems)
        }

        Spacer(Modifier.height(16.dp))

        if (!(widthSizeClass >= WindowWidthSizeClass.Medium || isLandscape)) {
            if (announcement != null) {
                AnnouncementCard(
                    title = announcement.title,
                    subtitle = announcement.subtitle
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        HelpSection(
            onAddClick = { /* TODO: hook action */ },
            actions = listOf("CS", "BS")
        )
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_loading"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.home_loading_message),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("home_error"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.home_error_message, message),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_empty"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.home_empty_message),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun GreetingHeader(userName: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.home_greeting_title, userName),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.home_greeting_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// UI model extracted to HomeMappers.kt

 

// Using designsystem AnnouncementCard

@Composable
private fun HelpSection(onAddClick: () -> Unit, actions: List<String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        SectionHeader(title = stringResource(R.string.home_help_title))

        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val labels = listOf(
                stringResource(R.string.action_label_cs),
                stringResource(R.string.action_label_bs)
            )
            labels.forEach { label ->
                ActionCircle(label = label, onClick = { /* TODO */ })
            }
        }

        Spacer(Modifier.height(8.dp))
        HorizontalDivider()
    }
}

// Using designsystem ActionCircle

@PreviewLightDark
@PreviewFontScale
@Preview(showBackground = true, name = "Home - EN", locale = "en")
@Composable
private fun HomeScreenPreviewEn() {
    SiboriTheme {
        HomeScreen(state = HomeUiState.Loading)
    }
}

@PreviewLightDark
@PreviewFontScale
@Preview(showBackground = true, name = "Home - ID", locale = "id")
@Composable
private fun HomeScreenPreviewId() {
    SiboriTheme {
        HomeScreen(state = HomeUiState.Empty)
    }
}

@PreviewScreenSizes
@Composable
private fun HomeScreenMultiPreview() {
    SiboriTheme {
        val demo = id.or.sutanto.sibori.core.domain.HomeData(
            userName = "Cayadi",
            nextAssignment = null,
            weekBadges = emptyList(),
            announcements = emptyList(),
            openNeedsCount = 0
        )
        HomeScreen(state = HomeUiState.Ready(demo))
    }
}
