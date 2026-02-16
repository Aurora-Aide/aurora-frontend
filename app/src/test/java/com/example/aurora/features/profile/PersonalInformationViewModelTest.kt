package com.example.aurora.features.profile

import com.example.aurora.MainDispatcherRule
import com.example.aurora.domain.usecase.GetUserUseCase
import com.example.aurora.domain.usecase.UpdateNamesUseCase
import com.example.aurora.features.signup.SignupNamesErrors
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
class PersonalInformationViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val updateUseCase = mockk<UpdateNamesUseCase>()
    private val getUserUseCase = mockk<GetUserUseCase>()
    private lateinit var viewModel: PersonalInformationViewModel

    @Before
    fun setUp() {
        coEvery { getUserUseCase.invoke() } returns Result.failure(IOException("offline"))
        viewModel = PersonalInformationViewModel(updateUseCase, getUserUseCase)
    }

    @Test
    fun validate_names_invalid_sets_error_enums() = runTest {
        viewModel.firstName("")
        viewModel.lastName("")
        viewModel.validateNames()

        val state = viewModel.personalInformation.value
        assertEquals(SignupNamesErrors.EMPTY_NAME, state.isFirstNameError)
        assertEquals(SignupNamesErrors.EMPTY_NAME, state.isLastNameError)
    }

    @Test
    fun update_names_failure_sets_error_message() = runTest {
        coEvery { updateUseCase.invoke(any(), any()) } returns Result.failure(IOException("offline"))

        viewModel.firstName("Test")
        viewModel.lastName("User")
        viewModel.validateNames()
        advanceUntilIdle()

        assertEquals("Network error. Check your connection.", viewModel.personalInformation.value.errorMessage)
    }
}
