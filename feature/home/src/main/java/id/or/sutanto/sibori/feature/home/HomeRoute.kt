package id.or.sutanto.sibori.feature.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute() {
    // Obtain VM via Hilt; state rendering will be implemented next
    val viewModel: HomeViewModel = hiltViewModel()
    // For now, keep existing HomeScreen without state-based rendering
    HomeScreen()
}
