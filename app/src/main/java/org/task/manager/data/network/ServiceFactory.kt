package org.task.manager.data.network

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.task.manager.BuildConfig
import org.task.manager.shared.service.SessionManagerService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


abstract class ServiceFactory(
    private val logLevel: HttpLoggingInterceptor.Level,
    private val sessionManagerService: SessionManagerService,
    private val context: Context
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
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.SERVER_URL)
        return builder.build()
    }

    private fun getHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val cacheSize = (5 * 1024 * 1024).toLong()
        val cacheConfig = Cache(context.cacheDir, cacheSize)
        interceptors(sessionManagerService, context).forEach { interceptor -> builder.addInterceptor(interceptor) }
        builder
            .cache(cacheConfig)
            .addInterceptor(HttpLoggingInterceptor().apply { level = logLevel })
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
        return builder.build()
    }

    abstract fun interceptors(sessionManagerService: SessionManagerService, context: Context): List<Interceptor>
}
