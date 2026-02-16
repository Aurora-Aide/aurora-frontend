package com.example.aurora.features.dispenser

import com.example.aurora.MainDispatcherRule
import com.example.aurora.domain.usecase.CreateScheduleUseCase
import com.example.aurora.domain.usecase.ListSchedulesUseCase
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
class AddScheduleViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val createUseCase = mockk<CreateScheduleUseCase>()
    private val listUseCase = mockk<ListSchedulesUseCase>(relaxed = true)
    private lateinit var viewModel: AddScheduleViewModel

    @Before
    fun setUp() {
        viewModel = AddScheduleViewModel(createUseCase, listUseCase)
    }

    @Test
    fun save_failure_sets_error_message() = runTest {
        coEvery { createUseCase.invoke(any(), any()) } returns Result.failure(IOException("offline"))

        viewModel.onDayChange(1)
        viewModel.onHourChange(9)
        viewModel.onMinuteChange(30)
        viewModel.save(containerId = 1)
        advanceUntilIdle()

        assertEquals("Network error. Check your connection.", viewModel.schedule.value.errorMessage)
    }
}
