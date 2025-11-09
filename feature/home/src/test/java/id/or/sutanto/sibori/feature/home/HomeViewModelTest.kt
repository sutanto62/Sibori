package id.or.sutanto.sibori.feature.home

import app.cash.turbine.test
import id.or.sutanto.sibori.core.data.HomeRepository
import id.or.sutanto.sibori.core.domain.GetHomeDataUseCase
import id.or.sutanto.sibori.core.domain.HomeData
import id.or.sutanto.sibori.core.model.Announcement
import id.or.sutanto.sibori.core.model.MinistryAssignment
import id.or.sutanto.sibori.core.model.MinistryType
import id.or.sutanto.sibori.core.model.ScheduleEventStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        kotlinx.coroutines.Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun ready_state_when_usecase_returns_data() = runTest(dispatcher) {
        val vm = HomeViewModel(GetHomeDataUseCase(FakeRepo(mode = Mode.DATA)))

        vm.state.test {
            assert(awaitItem() is HomeUiState.Loading)
            dispatcher.scheduler.advanceUntilIdle()
            val next = awaitItem()
            assert(next is HomeUiState.Ready)
        }
    }

    @Test
    fun empty_state_when_usecase_returns_null() = runTest(dispatcher) {
        val vm = HomeViewModel(GetHomeDataUseCase(FakeRepo(mode = Mode.EMPTY)))

        vm.state.test {
            assert(awaitItem() is HomeUiState.Loading)
            dispatcher.scheduler.advanceUntilIdle()
            val next = awaitItem()
            assert(next is HomeUiState.Empty)
        }
    }

    @Test
    fun error_state_when_usecase_throws() = runTest(dispatcher) {
        val vm = HomeViewModel(GetHomeDataUseCase(FakeRepo(mode = Mode.ERROR)))

        vm.state.test {
            assert(awaitItem() is HomeUiState.Loading)
            dispatcher.scheduler.advanceUntilIdle()
            val next = awaitItem()
            assert(next is HomeUiState.Error)
        }
    }

    private fun sampleHomeData() = HomeData(
        userName = "Test",
        nextAssignment = MinistryAssignment(
            id = "a1",
            scheduleEventId = 1L,
            scheduledAt = 0L,
            ministryType = MinistryType.PRIEST_ASSISTANT,
            status = ScheduleEventStatus.UNCONFIRMED,
        ),
        weekBadges = emptyList(),
        announcements = listOf(Announcement("id", "Title", "Sub")),
        openNeedsCount = 0
    )

    private enum class Mode { DATA, EMPTY, ERROR }

    private inner class FakeRepo(private val mode: Mode) : HomeRepository {
        override suspend fun getUserName(): String = if (mode == Mode.ERROR) error("boom") else "Test"
        override suspend fun getNextAssignment(): id.or.sutanto.sibori.core.model.MinistryAssignment? =
            when (mode) {
                Mode.DATA -> id.or.sutanto.sibori.core.model.MinistryAssignment(
                    id = "a1",
                    scheduleEventId = 1L,
                    scheduledAt = 0L,
                    ministryType = id.or.sutanto.sibori.core.model.MinistryType.PRIEST_ASSISTANT,
                    status = ScheduleEventStatus.UNCONFIRMED,
                )
                Mode.EMPTY, Mode.ERROR -> null
            }
        override suspend fun getWeekBadges(): List<id.or.sutanto.sibori.core.model.WeekBadge> = emptyList()
        override suspend fun getAnnouncements(): List<id.or.sutanto.sibori.core.model.Announcement> =
            if (mode == Mode.DATA) listOf(Announcement("id", "Title", "Sub")) else emptyList()
        override suspend fun getOpenNeedsCount(): Int = 0
    }
}
