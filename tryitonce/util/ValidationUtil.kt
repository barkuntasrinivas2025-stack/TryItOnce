package com.TRY.tryitonce.util

object ValidationUtil {
    private val EMAIL_REGEX = Regex("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$")

    fun validateEmail(email: String): String? = when {
        email.isBlank() -> "Email must not be empty"
        !EMAIL_REGEX.matches(email.trim()) -> "Enter a valid email address"
        else -> null
    }

    fun validatePassword(password: String): String? = when {
        password.length < 8 -> "Password must be at least 8 characters"
        !password.any { it.isDigit() } -> "Password must contain at least one digit"
        !password.any { it.isUpperCase() } -> "Password must contain at least one uppercase letter"
        !password.any { it.isLowerCase() } -> "Password must contain at least one lowercase letter"
        else -> null
    }

    fun validateDisplayName(name: String): String? = when {
        name.isBlank() -> "Name must not be empty"
        name.length < 2 -> "Name must be at least 2 characters"
        name.length > 50 -> "Name must not exceed 50 characters"
        else -> null
    }

    fun validatePasswordConfirmation(password: String, confirmPassword: String): String? =
        if (password != confirmPassword) "Passwords do not match" else null
}
