package id.or.sutanto.sibori.feature.home

import id.or.sutanto.sibori.core.domain.HomeData

/**
 * Home UI state for the HomeScreen.
 * Represents the different states the UI can be in.
 */
internal sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
    data object Empty : HomeUiState
    data class Ready(val data: HomeData) : HomeUiState
}
