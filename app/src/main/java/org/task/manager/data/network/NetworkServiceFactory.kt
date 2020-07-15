package org.task.manager.data.network

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.task.manager.shared.SessionManager

class NetworkServiceFactory(
    logLevel: HttpLoggingInterceptor.Level,
    sessionManager: SessionManager
) : ServiceFactory(logLevel, sessionManager) {

    override fun interceptors(sessionManager: SessionManager): List<Interceptor> {
        val token = sessionManager.fetchAuthToken()
        if(token != null) {
            val networkRequestHeader = NetworkRequestHeader("Authorization", "Bearer $token")
           return arrayListOf(NetworkRequestInterceptor(listOf(networkRequestHeader)))
        }
        return arrayListOf(NetworkRequestInterceptor(listOf()))
    }

}