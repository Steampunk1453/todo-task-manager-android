package org.task.manager.data.network.api

import org.task.manager.data.network.model.request.PasswordRequest
import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.data.network.model.request.UserRequest
import org.task.manager.data.network.model.response.UserResponse
import org.task.manager.shared.Constants.ACCOUNT_URL
import org.task.manager.shared.Constants.ACTIVATE_URL
import org.task.manager.shared.Constants.CHANGE_PASSWORD_URL
import org.task.manager.shared.Constants.REGISTER_URL
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountApi {

    @POST(REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest)

    @GET(ACTIVATE_URL)
    suspend fun activate(@Query("key") key: String)

    @GET(ACCOUNT_URL)
    suspend fun get(): Response<UserResponse>

    @POST(ACCOUNT_URL)
    suspend fun save(@Body userRequest: UserRequest)

    @POST(ACCOUNT_URL + CHANGE_PASSWORD_URL)
    suspend fun changePassword(@Body passwordRequest: PasswordRequest)

}