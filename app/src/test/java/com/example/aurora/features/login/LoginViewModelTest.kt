package com.example.aurora.features.login

import com.example.aurora.MainDispatcherRule
import com.example.aurora.data.entity.UserEntity
import com.example.aurora.domain.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val loginUseCase = mockk<LoginUseCase>()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun login_success_updates_state() = runTest {
        val user = UserEntity(
            email = "test@example.com",
            firstName = "Test",
            lastName = "User",
            isSuperuser = false,
            accessToken = "access",
            refreshToken = "refresh"
        )
        coEvery { loginUseCase.invoke("test@example.com", "Password1!") } returns Result.success(user)

        viewModel.email("test@example.com")
        viewModel.password("Password1!")
        viewModel.login()
        advanceUntilIdle()

        val state = viewModel.login.value
        assertTrue(state.isLoginSuccessful)
        assertEquals("", state.error)
        assertEquals("Test", state.firstName)
    }

    @Test
    fun login_failure_sets_error_message() = runTest {
        coEvery { loginUseCase.invoke(any(), any()) } returns Result.failure(IOException("offline"))

        viewModel.email("test@example.com")
        viewModel.password("Password1!")
        viewModel.login()
        advanceUntilIdle()

        val state = viewModel.login.value
        assertEquals("Network error. Check your connection.", state.error)
    }
}
