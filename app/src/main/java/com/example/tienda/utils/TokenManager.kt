package com.example.tienda.utils

import android.content.Context

object TokenManager {
    private const val PREFS_NAME = "APP_PREFS"
    private const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"
    private const val KEY_REFRESH_TOKEN = "REFRESH_TOKEN"

    fun saveTokens(context: Context, accessToken: String, refreshToken: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_REFRESH_TOKEN, refreshToken)
            apply()
        }
    }

    fun getAccessToken(context: Context): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_ACCESS_TOKEN, null)
    }
}
