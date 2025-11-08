package id.or.sutanto.sibori.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import id.or.sutanto.sibori.core.designsystem.components.ActionCircle
import id.or.sutanto.sibori.core.designsystem.components.SectionHeader
import id.or.sutanto.sibori.core.designsystem.theme.spacing
import id.or.sutanto.sibori.feature.home.R

@Composable
internal fun HelpSection(
    onAddClick: () -> Unit,
    actions: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SectionHeader(title = stringResource(R.string.home_help_title))

        Spacer(Modifier.height(MaterialTheme.spacing.lg))

        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)) {
            val labels = listOf(
                stringResource(R.string.action_label_cs),
                stringResource(R.string.action_label_bs)
            )
            labels.forEach { label ->
                ActionCircle(label = label, onClick = onAddClick)
            }
        }

        Spacer(Modifier.height(MaterialTheme.spacing.sm))
        HorizontalDivider()
    }
}
