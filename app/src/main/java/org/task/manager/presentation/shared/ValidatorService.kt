package org.task.manager.presentation.shared

import org.task.manager.R

private const val PATTER_ONLY_LETTERS_NUMBERS = "^[_.@A-Za-z0-9-]*$"
private const val NO_ERROR_MESSAGE = 0

class ValidatorService {

    fun isValidUsername(username: String): Pair<Boolean, Int> {
        val regex = PATTER_ONLY_LETTERS_NUMBERS.toRegex()
        if (username.isBlank()) return Pair(false, R.string.username_empty_error)
        else if (!regex.matches(username)) return Pair(false, R.string.username_pattern_error)
        return Pair(true, NO_ERROR_MESSAGE)
    }

    fun isValidEmail(email: String): Pair<Boolean, Int> {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return Pair(false,  R.string.email_error)
        return Pair(true, NO_ERROR_MESSAGE)
    }

    fun isValidPassword(password: String, passwordConfirmation: String): Pair<Boolean, Int> {
        if (password.length < 4 || passwordConfirmation.length < 4)
            return Pair(false, R.string.password_length_error)
        else if (password != passwordConfirmation) return Pair(false, R.string.password_match_error)
        return Pair(true, NO_ERROR_MESSAGE)
    }

}