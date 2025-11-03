package id.or.sutanto.sibori.feature.home

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import id.or.sutanto.sibori.core.data.FakeHomeRepository
import id.or.sutanto.sibori.core.data.HomeRepository
import id.or.sutanto.sibori.core.domain.GetHomeDataUseCase
import id.or.sutanto.sibori.core.model.Announcement
import id.or.sutanto.sibori.core.model.MinistryAssignment
import id.or.sutanto.sibori.core.model.WeekBadge

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeHomeRepository
    private lateinit var useCase: GetHomeDataUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        repository = FakeHomeRepository()
        useCase = GetHomeDataUseCase(repository)
        viewModel = HomeViewModel(useCase)
    }

    @After
    fun tearDown() {
        // Clean up if needed
    }

    @Test
    fun `initial state is Loading`() = runTest(testDispatcher) {
        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState is HomeUiState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refresh emits Ready state with data when repository returns data`() = runTest(testDispatcher) {
        viewModel.state.test {
            // Skip initial Loading state
            skipItems(1)
            
            viewModel.refresh()
            
            val readyState = awaitItem()
            assertTrue(readyState is HomeUiState.Ready)
            
            val data = (readyState as HomeUiState.Ready).data
            assertEquals("Cayadi", data.userName)
            assertTrue(data.weekBadges.isNotEmpty())
            assertTrue(data.announcements.isNotEmpty())
            assertEquals(3, data.openNeedsCount)
            
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refresh emits Loading then Ready when successful`() = runTest(testDispatcher) {
        viewModel.state.test {
            // Skip initial Loading state from init
            skipItems(1)
            
            viewModel.refresh()
            
            val loadingState = awaitItem()
            assertTrue(loadingState is HomeUiState.Loading)
            
            val readyState = awaitItem()
            assertTrue(readyState is HomeUiState.Ready)
            
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refresh emits Error state when use case throws exception`() = runTest(testDispatcher) {
        val errorRepository = object : HomeRepository {
            override suspend fun getUserName(): String {
                throw RuntimeException("Network error")
            }
            override suspend fun getNextAssignment(): MinistryAssignment? = null
            override suspend fun getWeekBadges(): List<WeekBadge> = emptyList()
            override suspend fun getAnnouncements(): List<Announcement> = emptyList()
            override suspend fun getOpenNeedsCount(): Int = 0
        }
        
        val errorUseCase = GetHomeDataUseCase(errorRepository)
        val errorViewModel = HomeViewModel(errorUseCase)
        
        errorViewModel.state.test {
            // Skip initial Loading state
            skipItems(1)
            
            errorViewModel.refresh()
            
            val errorState = awaitItem()
            assertTrue(errorState is HomeUiState.Error)
            
            val error = errorState as HomeUiState.Error
            assertTrue(error.message.contains("Network error"))
            
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refresh emits Empty state when use case returns null`() = runTest(testDispatcher) {
        val emptyRepository = object : HomeRepository {
            override suspend fun getUserName(): String = "Test"
            override suspend fun getNextAssignment(): MinistryAssignment? = null
            override suspend fun getWeekBadges(): List<WeekBadge> = emptyList()
            override suspend fun getAnnouncements(): List<Announcement> = emptyList()
            override suspend fun getOpenNeedsCount(): Int = 0
        }
        
        val emptyUseCase = GetHomeDataUseCase(emptyRepository)
        val emptyViewModel = HomeViewModel(emptyUseCase)
        
        emptyViewModel.state.test {
            // Skip initial Loading state
            skipItems(1)
            
            emptyViewModel.refresh()
            
            val emptyState = awaitItem()
            assertTrue(emptyState is HomeUiState.Empty)
            
            cancelAndIgnoreRemainingEvents()
        }
    }
}
