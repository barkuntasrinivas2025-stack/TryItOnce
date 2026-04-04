package com.TRY.tryitonce.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

@Singleton
class TokenManager @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val EXPIRES_AT_KEY   = longPreferencesKey("token_expires_at")
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String, expiresIn: Long) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = accessToken
            prefs[EXPIRES_AT_KEY]   = System.currentTimeMillis() + expiresIn * 1000L
        }
    }

    suspend fun isTokenValid(): Boolean {
        val expiresAt = context.dataStore.data.map { it[EXPIRES_AT_KEY] }.firstOrNull() ?: return false
        return System.currentTimeMillis() < expiresAt
    }

    suspend fun clearTokens() { context.dataStore.edit { it.clear() } }
}
