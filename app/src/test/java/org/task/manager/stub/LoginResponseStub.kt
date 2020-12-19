package org.task.manager.stub

import org.task.manager.data.network.model.response.LoginResponse

open class LoginResponseStub {

    companion object {

        fun random(
            statusCode: Int = 201,
            authToken: String = RandomGenerator.getString(),
        ) = LoginResponse(
            statusCode,
            authToken,
        )
    }

}
