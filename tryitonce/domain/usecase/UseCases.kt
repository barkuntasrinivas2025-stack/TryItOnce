package com.TRY.tryitonce.domain.usecase

import com.TRY.tryitonce.domain.model.User
import com.TRY.tryitonce.domain.repository.AuthRepository
import com.TRY.tryitonce.util.Resource
import com.TRY.tryitonce.util.ValidationUtil
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Resource<User> {
        ValidationUtil.validateEmail(email)?.let { return Resource.Error(it) }
        ValidationUtil.validatePassword(password)?.let { return Resource.Error(it) }
        return repo.login(email, password)
    }
}

class RegisterUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(
        displayName: String, email: String,
        password: String, confirmPassword: String
    ): Resource<User> {
        ValidationUtil.validateDisplayName(displayName)?.let { return Resource.Error(it) }
        ValidationUtil.validateEmail(email)?.let { return Resource.Error(it) }
        ValidationUtil.validatePassword(password)?.let { return Resource.Error(it) }
        ValidationUtil.validatePasswordConfirmation(password, confirmPassword)?.let { return Resource.Error(it) }
        return repo.register(email, password, displayName)
    }
}

class LogoutUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(): Resource<Unit> = repo.logout()
}

class GetCurrentUserUseCase @Inject constructor(private val repo: AuthRepository) {
    operator fun invoke(): Flow<User?> = repo.currentUser()
}

class IsLoggedInUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(): Boolean = repo.isLoggedIn()
}
