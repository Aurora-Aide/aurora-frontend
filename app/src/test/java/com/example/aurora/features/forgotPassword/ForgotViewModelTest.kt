package com.example.aurora.features.forgotPassword

import com.example.aurora.MainDispatcherRule
import com.example.aurora.domain.usecase.ForgotPassUseCase
import com.example.aurora.domain.usecase.ResetPassUseCase
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
class ForgotViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val forgotUseCase = mockk<ForgotPassUseCase>()
    private val resetUseCase = mockk<ResetPassUseCase>()
    private lateinit var viewModel: ForgotViewModel

    @Before
    fun setUp() {
        viewModel = ForgotViewModel(forgotUseCase, resetUseCase)
    }

    @Test
    fun validate_email_failure_sets_error_message() = runTest {
        coEvery { forgotUseCase.invoke(any()) } returns Result.failure(IOException("offline"))

        viewModel.email("test@example.com")
        viewModel.validateEmail()
        advanceUntilIdle()

        assertEquals("Network error. Check your connection.", viewModel.data.value.errorMessage)
    }
}
