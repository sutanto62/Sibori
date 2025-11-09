package id.or.sutanto.sibori.feature.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import id.or.sutanto.sibori.core.domain.HomeData
import id.or.sutanto.sibori.core.model.Announcement
import id.or.sutanto.sibori.core.model.MinistryAssignment
import id.or.sutanto.sibori.core.model.MinistryType
import id.or.sutanto.sibori.core.model.ScheduleEventStatus
import id.or.sutanto.sibori.core.model.WeekBadge
import id.or.sutanto.sibori.core.model.WeekEmphasis
import id.or.sutanto.sibori.core.model.ConfirmationStatus

internal class HomeStatePreviewParamProvider : PreviewParameterProvider<HomeUiState> {
    override val values: Sequence<HomeUiState> = sequenceOf(
        HomeUiState.Loading,
        HomeUiState.Error("Network error"),
        HomeUiState.Empty,
        HomeUiState.Ready(
            HomeData(
                userName = "Cayadi",
                    nextAssignment = MinistryAssignment(
                        id = "assign_prev",
                        scheduleEventId = 1L,
                        scheduledAt = System.currentTimeMillis() + 3 * 60 * 60 * 1000,
                        ministryType = MinistryType.PRIEST_ASSISTANT,
                        status = ScheduleEventStatus.UNCONFIRMED,
                    ),
                weekBadges = listOf(
                    WeekBadge(label = "M1", emphasis = WeekEmphasis.Primary, confirmation = ConfirmationStatus.Confirmed),
                    WeekBadge(label = "M2", confirmation = ConfirmationStatus.Confirmed),
                    WeekBadge(label = "S1", confirmation = ConfirmationStatus.Done),
                    WeekBadge(label = "GM2", confirmation = ConfirmationStatus.None),
                ),
                announcements = listOf(
                    Announcement("ann_1", "Reminder: Rehearsal on Friday", "Choir rehearsal at 7 PM in the hall")
                ),
                openNeedsCount = 2
            )
        )
    )
}