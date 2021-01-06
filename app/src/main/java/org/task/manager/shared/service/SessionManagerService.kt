package org.task.manager.shared.service

import android.content.SharedPreferences

class SessionManagerService(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return sharedPreferences.getString(USER_TOKEN, null)
    }

    fun removeAuthToken() {
        val editor = sharedPreferences.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }

}