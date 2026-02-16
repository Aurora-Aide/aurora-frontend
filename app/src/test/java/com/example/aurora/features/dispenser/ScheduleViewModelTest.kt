package com.example.aurora.features.dispenser

import com.example.aurora.MainDispatcherRule
import com.example.aurora.domain.usecase.DeleteScheduleUseCase
import com.example.aurora.domain.usecase.GetScheduleUseCase
import com.example.aurora.domain.usecase.UpdateScheduleUseCase
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ScheduleViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUseCase = mockk<GetScheduleUseCase>(relaxed = true)
    private val updateUseCase = mockk<UpdateScheduleUseCase>()
    private val deleteUseCase = mockk<DeleteScheduleUseCase>(relaxed = true)
    private lateinit var viewModel: ScheduleViewModel

    @Before
    fun setUp() {
        viewModel = ScheduleViewModel(getUseCase, updateUseCase, deleteUseCase)
    }

    @Test
    fun save_failure_sets_error_message() = runTest {
        coEvery { updateUseCase.invoke(any(), any()) } returns Result.failure(IOException("offline"))

        viewModel.onDayChange(2)
        viewModel.onHourChange(10)
        viewModel.onMinuteChange(15)
        viewModel.save()
        advanceUntilIdle()

        assertEquals("Network error. Check your connection.", viewModel.schedule.value.errorMessage)
    }
}
