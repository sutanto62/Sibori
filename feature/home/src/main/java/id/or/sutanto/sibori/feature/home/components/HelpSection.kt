package id.or.sutanto.sibori.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.or.sutanto.sibori.core.designsystem.components.ActionCircle
import id.or.sutanto.sibori.core.designsystem.components.SectionHeader
import id.or.sutanto.sibori.feature.home.R

@Composable
internal fun HelpSection(
    onAddClick: () -> Unit,
    actions: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SectionHeader(title = stringResource(R.string.home_help_title))

        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val labels = listOf(
                stringResource(R.string.action_label_cs),
                stringResource(R.string.action_label_bs)
            )
            labels.forEach { label ->
                ActionCircle(label = label, onClick = onAddClick)
            }
        }

        Spacer(Modifier.height(8.dp))
        HorizontalDivider()
    }
}
