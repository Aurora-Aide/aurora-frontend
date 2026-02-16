package com.example.aurora.features.home

import com.example.aurora.MainDispatcherRule
import com.example.aurora.data.entity.DispenserEntity
import com.example.aurora.domain.usecase.AddDispenserUseCase
import com.example.aurora.domain.usecase.ListAllUserDispensersUseCase
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
class AddDispenserViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val addUseCase = mockk<AddDispenserUseCase>()
    private val listUseCase = mockk<ListAllUserDispensersUseCase>(relaxed = true)
    private lateinit var viewModel: AddDispenserViewModel

    @Before
    fun setUp() {
        viewModel = AddDispenserViewModel(addUseCase, listUseCase)
    }

    @Test
    fun validate_add_failure_sets_error_message() = runTest {
        coEvery { addUseCase.invoke(any(), any()) } returns Result.failure(IOException("offline"))

        viewModel.id("ABC-12345678-1234")
        viewModel.name("Dispenser One")
        viewModel.validate()
        advanceUntilIdle()

        assertEquals("Network error. Check your connection.", viewModel.dispenser.value.errorMessage)
    }
}
