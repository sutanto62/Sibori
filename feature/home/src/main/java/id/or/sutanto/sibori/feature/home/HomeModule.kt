package id.or.sutanto.sibori.feature.home

import id.or.sutanto.sibori.core.data.FakeHomeRepository
import id.or.sutanto.sibori.core.data.HomeRepository

/**
 * Dependency Injection module for Home feature.
 * Provides bindings for HomeRepository and GetHomeDataUseCase.
 * 
 * In a production setup, this could be migrated to Hilt/Dagger,
 * but for now we provide a simple factory approach.
 */
object HomeModule {
    /**
     * Provides the HomeRepository instance.
     * Currently returns FakeHomeRepository, but in production
     * this would provide the real implementation based on configuration.
     */
    fun provideHomeRepository(): HomeRepository = FakeHomeRepository()

    /**
     * Provides the HomeViewModelFactory instance.
     */
    fun provideHomeViewModelFactory(
        repository: HomeRepository = provideHomeRepository()
    ): HomeViewModelFactory {
        return HomeViewModelFactory(repository)
    }
}
