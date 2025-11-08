package id.or.sutanto.sibori.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun LoadingState(modifier: Modifier = Modifier) {
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
internal fun ErrorState(
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
internal fun EmptyState(modifier: Modifier = Modifier) {
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
internal fun GreetingHeader(userName: String, modifier: Modifier = Modifier) {
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
