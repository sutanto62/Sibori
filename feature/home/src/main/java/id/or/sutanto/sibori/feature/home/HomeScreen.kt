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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import id.or.sutanto.sibori.core.designsystem.components.AnnouncementCard
import id.or.sutanto.sibori.core.designsystem.theme.SiboriTheme
import id.or.sutanto.sibori.core.designsystem.theme.spacing
import id.or.sutanto.sibori.core.domain.HomeData
import id.or.sutanto.sibori.feature.home.components.HelpSection
import id.or.sutanto.sibori.feature.home.components.ThisWeekCard

@Composable
fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    widthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    onRetry: () -> Unit = {},
) {
    Box(modifier = modifier.statusBarsPadding()) {
        when (state) {
            is HomeUiState.Loading -> {
                LoadingState(modifier = Modifier)
            }
            is HomeUiState.Error -> {
                ErrorState(
                    message = state.message,
                    onRetry = onRetry,
                    modifier = Modifier
                )
            }
            is HomeUiState.Empty -> {
                EmptyState(modifier = Modifier)
            }
            is HomeUiState.Ready -> {
                HomeContent(
                    data = state.data,
                    widthSizeClass = widthSizeClass,
                    modifier = Modifier
                )
            }
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
            .padding(MaterialTheme.spacing.lg)
    ) {
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
        GreetingHeader(userName = data.userName)

        Spacer(Modifier.height(MaterialTheme.spacing.lg))

        // Map WeekBadge to ThisWeekItem for UI
        val weekItems = data.weekBadges.toThisWeekItems()

        // Get first announcement or use default
        val announcement = data.announcements.firstOrNull()

        if (widthSizeClass >= WindowWidthSizeClass.Medium || isLandscape) {
            Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg), modifier = Modifier.fillMaxWidth()) {
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

        Spacer(Modifier.height(MaterialTheme.spacing.lg))

        if (!(widthSizeClass >= WindowWidthSizeClass.Medium || isLandscape)) {
            if (announcement != null) {
                AnnouncementCard(
                    title = announcement.title,
                    subtitle = announcement.subtitle
                )
            }
        }

        Spacer(Modifier.height(MaterialTheme.spacing.xl))

        HelpSection(
            onAddClick = { /* TODO: hook action */ },
            actions = listOf("CS", "BS")
        )
    }
}

// Sections extracted to HomeSections.kt

@PreviewScreenSizes
@Composable
private fun HomeScreenMultiPreview() {
    SiboriTheme {
        val demo = HomeData(
            userName = "Cayadi",
            nextAssignment = null,
            weekBadges = emptyList(),
            announcements = emptyList(),
            openNeedsCount = 0
        )
        HomeScreen(state = HomeUiState.Ready(demo))
    }
}

@PreviewLightDark
@Preview(showBackground = true, name = "Home - Param States")
@Composable
private fun HomeScreenStatesPreview(
    @PreviewParameter(HomeStatePreviewParamProvider::class) state: HomeUiState
) {
    SiboriTheme {
        HomeScreen(state = state)
    }
}
