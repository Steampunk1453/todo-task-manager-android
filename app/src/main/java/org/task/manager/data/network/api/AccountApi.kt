package org.task.manager.data.network.api

import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.shared.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountApi {

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest)

    @GET(Constants.ACTIVATE_URL)
    suspend fun activate(@Query("key") key: String)

}