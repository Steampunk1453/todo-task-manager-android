package org.task.manager.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.task.manager.BuildConfig
import org.task.manager.shared.service.SessionManagerService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


abstract class ServiceFactory(
    private val logLevel: HttpLoggingInterceptor.Level,
    private val sessionManagerService: SessionManagerService
) {

    fun <T> create(serviceType: Class<T>): T {
        return create(
            serviceType,
            getHttpClient()
        )
    }

    private fun <T> create(
        serviceType: Class<T>,
        client: OkHttpClient
    ): T {
        val retrofit = getNetAdapter(client)
        return retrofit.create(serviceType)
    }

    private fun getNetAdapter(
        client: OkHttpClient
    ): Retrofit {
        val builder = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.SERVER_URL)
        return builder.build()
    }

    private fun getHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        interceptors(sessionManagerService).forEach { interceptor -> builder.addInterceptor(interceptor) }
        builder.addInterceptor(HttpLoggingInterceptor().apply { level = logLevel })
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
        return builder.build()
    }

    abstract fun interceptors(sessionManagerService: SessionManagerService): List<Interceptor>
}
