package id.or.sutanto.sibori.core.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.or.sutanto.sibori.core.data.HomeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun provideGetHomeDataUseCase(
        repository: HomeRepository,
    ): GetHomeDataUseCase = GetHomeDataUseCase(repository)
}
