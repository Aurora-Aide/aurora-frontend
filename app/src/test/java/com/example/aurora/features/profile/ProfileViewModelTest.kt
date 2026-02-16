package com.example.aurora.features.profile

import com.example.aurora.MainDispatcherRule
import com.example.aurora.domain.usecase.DeleteUserUseCase
import com.example.aurora.domain.usecase.GetUserUseCase
import com.example.aurora.domain.usecase.LogoutUseCase
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
class ProfileViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val logoutUseCase = mockk<LogoutUseCase>(relaxed = true)
    private val getUserUseCase = mockk<GetUserUseCase>()
    private val deleteUserUseCase = mockk<DeleteUserUseCase>(relaxed = true)
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        viewModel = ProfileViewModel(logoutUseCase, getUserUseCase, deleteUserUseCase)
    }

    @Test
    fun get_user_failure_sets_error_message() = runTest {
        coEvery { getUserUseCase.invoke() } returns Result.failure(IOException("offline"))

        viewModel.getUser()
        advanceUntilIdle()

        assertEquals("Network error. Check your connection.", viewModel.personalInfo.value.errorMessage)
    }
}
