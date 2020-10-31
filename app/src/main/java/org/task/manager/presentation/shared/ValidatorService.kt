package org.task.manager.presentation.shared

private const val USERNAME_EMPTY_ERROR_MESSAGE = "Your username is required"
private const val USERNAME_PATTERN_ERROR_MESSAGE = "Your username can only contain letters and digits"
private const val EMAIL_ERROR_MESSAGE = "The email isn't correct"
private const val PASSWORD_LENGTH_ERROR_MESSAGE = "The password is required to be at least 4 characters"
private const val PASSWORD_MATCH_ERROR_MESSAGE = "The password and its confirmation do not match!"

class ValidatorService {

    fun isValidUsername(username: String): Pair<Boolean, String> {
        val regex = "^[_.@A-Za-z0-9-]*$".toRegex()
        if (!username.isNotBlank()) return Pair(false, USERNAME_EMPTY_ERROR_MESSAGE)
        else if (!regex.matches(username)) return Pair(false, USERNAME_PATTERN_ERROR_MESSAGE)
        return Pair(true, "")
    }

    fun isValidEmail(email: String): Pair<Boolean, String> {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return Pair(false, EMAIL_ERROR_MESSAGE)
        return Pair(true, "")
    }

    fun isValidPassword(password: String, passwordConfirmation: String): Pair<Boolean, String> {
        if (password.length < 4 || passwordConfirmation.length < 4)
            return Pair(false, PASSWORD_LENGTH_ERROR_MESSAGE)
        else if (password != passwordConfirmation) return Pair(false, PASSWORD_MATCH_ERROR_MESSAGE)
        return Pair(true, "")
    }

}