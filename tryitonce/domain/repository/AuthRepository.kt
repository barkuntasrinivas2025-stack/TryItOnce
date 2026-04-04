package com.TRY.tryitonce.domain.repository

import com.TRY.tryitonce.domain.model.User
import com.TRY.tryitonce.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<User>
    suspend fun register(email: String, password: String, displayName: String): Resource<User>
    suspend fun logout(): Resource<Unit>
    fun currentUser(): Flow<User?>
    suspend fun isLoggedIn(): Boolean
}
