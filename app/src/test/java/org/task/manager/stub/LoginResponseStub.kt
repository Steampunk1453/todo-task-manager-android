package org.task.manager.stub

import org.task.manager.data.network.model.response.LoginResponse

open class LoginResponseStub {

    companion object {

        fun random(
            statusCode: Int = 201,
            authToken: String = getRandomString(36)
        ) = LoginResponse(
            statusCode,
            authToken,
        )
    }

}

private fun getRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}