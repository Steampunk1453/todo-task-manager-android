package org.task.manager.shared

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

}