package org.task.manager.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.task.manager.shared.service.SessionManagerService

private const val CACHE_STORE_SECONDS = 5
private const val CACHE_STORE_DAYS = 60 * 60 * 24 * 7

class NetworkServiceFactory(
    logLevel: HttpLoggingInterceptor.Level,
    sessionManagerService: SessionManagerService,
    context: Context
) : ServiceFactory(logLevel, sessionManagerService, context) {

    override fun interceptors(
        sessionManagerService: SessionManagerService,
        context: Context
    ): List<Interceptor> {
        val cacheRequestHeader = buildCacheRequestHeader(context)
        val token = sessionManagerService.fetchAuthToken()
        if (token != null) {
            val tokenAuthorizationRequestHeader = buildAuthorizationRequestHeader(token)
            return arrayListOf(NetworkRequestInterceptor(listOf(tokenAuthorizationRequestHeader, cacheRequestHeader)))
        }
        return arrayListOf(NetworkRequestInterceptor(listOf(cacheRequestHeader)))
    }

    private fun buildAuthorizationRequestHeader(token: String): NetworkRequestHeader {
        return NetworkRequestHeader("Authorization", "Bearer $token")
    }

    private fun buildCacheRequestHeader(context: Context): NetworkRequestHeader {
        /*
        *  If there is Internet, get the cache that was stored 5 seconds ago.
        *  If the cache is older than 5 seconds, then discard it,
        *  and indicate an error in fetching the response.
        *  The 'max-age' attribute is responsible for this behavior.
        */
        return if (hasNetwork(context)) NetworkRequestHeader(
            "Cache-Control",
            "public, max-age=$CACHE_STORE_SECONDS"
        )
        /*
          *  If there is no Internet, get the cache that was stored 7 days ago.
          *  If the cache is older than 7 days, then discard it,
          *  and indicate an error in fetching the response.
          *  The 'max-stale' attribute is responsible for this behavior.
          *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
         */
        else NetworkRequestHeader(
            "Cache-Control",
            "public, only-if-cached, max-stale=$CACHE_STORE_DAYS"
        )
    }

    private fun hasNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true

    }

}