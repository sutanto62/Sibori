package id.or.sutanto.sibori.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.or.sutanto.sibori.core.designsystem.components.WeekCircle
import id.or.sutanto.sibori.feature.home.R
import androidx.compose.ui.res.stringResource
import id.or.sutanto.sibori.core.designsystem.components.WeekCircleEmphasis
import id.or.sutanto.sibori.core.designsystem.components.WeekCircleIndicator
import androidx.compose.material3.MaterialTheme
import id.or.sutanto.sibori.feature.home.ThisWeekItem
import id.or.sutanto.sibori.core.designsystem.theme.spacing
import id.or.sutanto.sibori.core.designsystem.theme.radii

@Composable
internal fun ThisWeekCard(items: List<ThisWeekItem>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(MaterialTheme.radii.lg),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.lg)) {
            Text(
                text = stringResource(R.string.home_this_week),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.md))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
                items(items.size) { index ->
                    val item = items[index]
                    WeekCircle(
                        label = item.label,
                        emphasis = item.emphasis,
                        indicator = item.indicator
                    )
                }
            }
        }
    }
}
