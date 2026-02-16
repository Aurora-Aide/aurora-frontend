package com.example.aurora.features.signup

import com.example.aurora.MainDispatcherRule
import com.example.aurora.data.entity.UserEntity
import com.example.aurora.domain.usecase.SignupUseCase
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignupViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val signupUseCase = mockk<SignupUseCase>()
    private lateinit var viewModel: SignupViewModel

    @Before
    fun setUp() {
        viewModel = SignupViewModel(signupUseCase)
    }

    @Test
    fun validate_first_step_with_valid_inputs_advances_step() = runTest {
        viewModel.email("test@example.com")
        viewModel.password("Password1!")
        viewModel.passwordRepeat("Password1!")

        viewModel.validateFirstStep()

        assertFalse(viewModel.signup.value.isFirstStep)
    }

    @Test
    fun validate_second_step_failure_sets_error_message() = runTest {
        val user = UserEntity(email = "test@example.com", firstName = "Test", lastName = "User")
        coEvery { signupUseCase.invoke(any(), any(), any(), any()) } returns Result.failure(IOException("offline"))

        viewModel.email("test@example.com")
        viewModel.password("Password1!")
        viewModel.passwordRepeat("Password1!")
        viewModel.validateFirstStep()

        viewModel.firstName("Test")
        viewModel.lastName("User")
        viewModel.validateSecondStep()
        advanceUntilIdle()

        assertEquals("Network error. Check your connection.", viewModel.signup.value.error)
    }
}
