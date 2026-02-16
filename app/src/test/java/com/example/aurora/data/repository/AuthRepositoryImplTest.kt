package com.example.aurora.data.repository

import com.example.aurora.data.entity.UserEntity
import com.example.aurora.data.model.Tokens
import com.example.aurora.data.model.UserModel
import com.example.aurora.data.sorce.AuthDataSource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryImplTest {
    private val dataSource = mockk<AuthDataSource>()
    private val tokenStorage = mockk<TokenStorage>(relaxed = true)
    private lateinit var repository: AuthRepositoryImpl

    @Before
    fun setUp() {
        repository = AuthRepositoryImpl(dataSource, tokenStorage)
    }

    @Test
    fun login_success_saves_tokens_and_maps_user() = runTest {
        val tokens = Tokens(
            access = "access",
            refresh = "refresh",
            user = UserModel(
                email = "test@example.com",
                firstName = "Test",
                lastName = "User",
                isSuperuser = false
            )
        )
        coEvery { dataSource.login("test@example.com", "Password1!") } returns Result.success(tokens)

        val result = repository.login("test@example.com", "Password1!")

        assertTrue(result.isSuccess)
        val user = result.getOrNull()
        assertEquals("access", user?.accessToken)
        assertEquals("refresh", user?.refreshToken)
        verify { tokenStorage.saveTokens("access", "refresh") }
    }

    @Test
    fun login_failure_propagates_error() = runTest {
        coEvery { dataSource.login(any(), any()) } returns Result.failure(IOException("offline"))

        val result = repository.login("test@example.com", "Password1!")

        assertTrue(result.isFailure)
    }
}
