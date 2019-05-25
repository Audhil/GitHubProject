package com.audhil.medium.samplegithubapp.di.modules

import android.content.Context
import android.content.pm.PackageManager
import com.audhil.medium.samplegithubapp.SampleGitHubApp
import com.audhil.medium.samplegithubapp.data.AppAPIs
import com.audhil.medium.samplegithubapp.util.ConstantsUtil
import com.audhil.medium.samplegithubapp.util.GLog
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

//  Application module
@Module
class ApplicationModule(private val application: SampleGitHubApp) {

    @Provides
    @Singleton
    internal fun giveContext(): Context = this.application

    @Provides
    @Singleton
    internal fun givePackageManager(): PackageManager = application.packageManager
}

//  API module
@Module(includes = [(ApplicationModule::class)])
class APIModule {

    @Provides
    @Singleton
    fun giveLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            GLog.v("APP LOGG", message)
        })
        interceptor.level = if (GLog.DEBUG_BOOL)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    //  okHttpClient
    @Provides
    fun giveOkHttpClient(
        context: Context,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            //  increasing time outs
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    fun giveRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(ConstantsUtil.GITHUB_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    @Provides
    fun giveRetrofitService(okHttpClient: OkHttpClient): AppAPIs =
        Retrofit.Builder()
            .baseUrl(ConstantsUtil.GITHUB_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AppAPIs::class.java)
}