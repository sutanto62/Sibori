package id.or.sutanto.sibori.feature.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun HomeRoute() {
    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle().value
    HomeScreen(state = state, onRetry = { viewModel.refresh() })
}
