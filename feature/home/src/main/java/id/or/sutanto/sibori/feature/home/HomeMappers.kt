package id.or.sutanto.sibori.feature.home

import id.or.sutanto.sibori.core.designsystem.components.WeekCircleEmphasis
import id.or.sutanto.sibori.core.designsystem.components.WeekCircleIndicator
import id.or.sutanto.sibori.core.model.ConfirmationStatus
import id.or.sutanto.sibori.core.model.WeekBadge
import id.or.sutanto.sibori.core.model.WeekEmphasis

internal data class ThisWeekItem(
    val label: String,
    val emphasis: WeekCircleEmphasis = WeekCircleEmphasis.Neutral,
    val indicator: WeekCircleIndicator = WeekCircleIndicator.None,
)

internal fun List<WeekBadge>.toThisWeekItems(): List<ThisWeekItem> = map { it.toThisWeekItem() }

internal fun WeekBadge.toThisWeekItem(): ThisWeekItem = ThisWeekItem(
    label = label,
    emphasis = when (emphasis) {
        WeekEmphasis.Primary -> WeekCircleEmphasis.Primary
        WeekEmphasis.Neutral -> WeekCircleEmphasis.Neutral
    },
    indicator = confirmation.toIndicator()
)

internal fun ConfirmationStatus.toIndicator(): WeekCircleIndicator = when (this) {
    ConfirmationStatus.Confirmed -> WeekCircleIndicator.Confirmed
    ConfirmationStatus.Done -> WeekCircleIndicator.Done
    ConfirmationStatus.None -> WeekCircleIndicator.None
}
