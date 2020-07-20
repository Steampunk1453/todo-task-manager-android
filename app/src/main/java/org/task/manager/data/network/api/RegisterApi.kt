package org.task.manager.data.network.api

import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.shared.Constants
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest)

}