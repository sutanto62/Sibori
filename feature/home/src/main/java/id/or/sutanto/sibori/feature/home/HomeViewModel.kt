package id.or.sutanto.sibori.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.or.sutanto.sibori.core.domain.GetHomeDataUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeData: GetHomeDataUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val state: StateFlow<HomeUiState> = _state

    init { refresh() }

    fun refresh() {
        _state.value = HomeUiState.Loading
        viewModelScope.launch {
            runCatching { getHomeData() }
                .onSuccess { data ->
                    _state.value = if (data == null) HomeUiState.Empty else HomeUiState.Ready(data)
                }
                .onFailure { throwable ->
                    _state.value = HomeUiState.Error(throwable.message ?: "Unknown error")
                }
        }
    }
}
