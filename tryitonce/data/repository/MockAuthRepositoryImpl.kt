package com.TRY.tryitonce.data.repository

import com.TRY.tryitonce.data.local.TokenManager
import com.TRY.tryitonce.domain.model.User
import com.TRY.tryitonce.domain.repository.AuthRepository
import com.TRY.tryitonce.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockAuthRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
) : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    private val registeredUsers = mutableMapOf<String, Pair<String, User>>()

    override suspend fun login(email: String, password: String): Resource<User> {
        delay(1200)
        val stored = registeredUsers[email.lowercase()]
        val user = when {
            stored != null && stored.first != password ->
                return Resource.Error("Unauthorized. Invalid credentials.")
            stored != null -> stored.second
            else -> User(
                id = "demo-001",
                email = email,
                displayName = email.substringBefore("@").replaceFirstChar { it.uppercase() },
                isEmailVerified = true,
            )
        }
        tokenManager.saveTokens("mock-access", "mock-refresh", 3600L)
        _currentUser.value = user
        return Resource.Success(user)
    }

    override suspend fun register(email: String, password: String, displayName: String): Resource<User> {
        delay(1500)
        if (registeredUsers.containsKey(email.lowercase()))
            return Resource.Error("An account with this email already exists.")
        val user = User(
            id = "user-${System.currentTimeMillis()}",
            email = email,
            displayName = displayName,
            isEmailVerified = false,
        )
        registeredUsers[email.lowercase()] = Pair(password, user)
        tokenManager.saveTokens("mock-access", "mock-refresh", 3600L)
        _currentUser.value = user
        return Resource.Success(user)
    }

    override suspend fun logout(): Resource<Unit> {
        delay(300)
        tokenManager.clearTokens()
        _currentUser.value = null
        return Resource.Success(Unit)
    }

    override fun currentUser(): Flow<User?> = _currentUser.asStateFlow()
    override suspend fun isLoggedIn(): Boolean = tokenManager.isTokenValid()
}
