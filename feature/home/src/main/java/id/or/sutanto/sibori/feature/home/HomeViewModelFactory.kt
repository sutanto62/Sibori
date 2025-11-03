package id.or.sutanto.sibori.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.or.sutanto.sibori.core.data.HomeRepository
import id.or.sutanto.sibori.core.domain.GetHomeDataUseCase

class HomeViewModelFactory(
    private val repository: HomeRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val useCase = GetHomeDataUseCase(repository)
            return HomeViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
