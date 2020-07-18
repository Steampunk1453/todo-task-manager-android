package org.task.manager.data.network

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.task.manager.shared.SessionManagerService

class NetworkServiceFactory(
    logLevel: HttpLoggingInterceptor.Level,
    sessionManagerService: SessionManagerService
) : ServiceFactory(logLevel, sessionManagerService) {

    override fun interceptors(sessionManagerService: SessionManagerService): List<Interceptor> {
        val token = sessionManagerService.fetchAuthToken()
        if(token != null) {
            val networkRequestHeader = NetworkRequestHeader("Authorization", "Bearer $token")
           return arrayListOf(NetworkRequestInterceptor(listOf(networkRequestHeader)))
        }
        return arrayListOf(NetworkRequestInterceptor(listOf()))
    }

}