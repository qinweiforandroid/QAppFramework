package com.qw.framework.app

import android.app.Application
import com.qw.framework.App
import com.qw.framework.app.repository.API
import com.qw.framework.app.repository.RequestManager
import com.qw.network.env.AbstractHost
import com.qw.network.env.Env
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.util.concurrent.TimeUnit

/**
 * Created by qinwei on 2024/1/16 21:17
 * email: qinwei_it@163.com
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        App.init(
            App.Builder(this)
                .setDebug(BuildConfig.DEBUG)
        )
        initHttp()
    }

    private fun initHttp() {
        AbstractHost.setEnv(Env.ENV_RELEASE)
        val okHttp = OkHttpClient.Builder()
            .authenticator(object : Authenticator {
                override fun authenticate(route: Route?, response: Response): Request? {
                    val newBuilder = response.request.newBuilder()
                    newBuilder.addHeader("token", "new token")
                    return newBuilder.build()
                }
            })
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .apply {
                if (BuildConfig.DEBUG) {
//                    val loggingInterceptor = HttpLoggingInterceptor()
//                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//                    addInterceptor(loggingInterceptor)
                    addInterceptor(requestInterceptor)
//                    addInterceptor(responseInterceptor)
                }
            }
        RequestManager.putOkHttpClient(API.domain, okHttp.build())
    }

    private val requestInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
                .newBuilder()
                .apply {
//                    if (Session.isLogin) {
//                        addHeader("Authorization", Session.getAuthorization())
//                    }
                }.build()
            return chain.proceed(request)
        }
    }
}