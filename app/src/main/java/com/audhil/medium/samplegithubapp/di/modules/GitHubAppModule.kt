package com.audhil.medium.samplegithubapp.di.modules

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.room.Room
import com.audhil.medium.samplegithubapp.GitHubDelegate
import com.audhil.medium.samplegithubapp.data.local.dao.PullDao
import com.audhil.medium.samplegithubapp.data.local.db.AppDataBase
import com.audhil.medium.samplegithubapp.data.remote.AppAPIs
import com.audhil.medium.samplegithubapp.repository.AppRepository
import com.audhil.medium.samplegithubapp.util.ConstantsUtil
import com.audhil.medium.samplegithubapp.util.GLog
import com.google.gson.Gson
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
class ApplicationModule(private val application: GitHubDelegate) {

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

    @Provides
    @Singleton
    fun giveGSONInstance(): Gson = Gson()
}

//  shared pref
@Module
class SharedPreferenceModule {

    @Provides
    @Singleton
    fun giveAppPreference(): SharedPreferences =
        GitHubDelegate.INSTANCE.getSharedPreferences(ConstantsUtil.APP_PREF_NAME, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    @Provides
    @Singleton
    fun giveAppPreferenceEditor(pref: SharedPreferences): SharedPreferences.Editor = pref.edit()
}

//  Database module
@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun giveUseInMemoryDB(): Boolean = false    /*  make "useInMemory = true" in TestCases */

    @Provides
    @Singleton
    fun giveAppDataBase(context: Context, useInMemory: Boolean): AppDataBase {
        val databaseBuilder = if (useInMemory)
            Room.inMemoryDatabaseBuilder(
                context,
                AppDataBase::class.java
            )
        else
            Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                AppDataBase.dbName
            )

        return databaseBuilder
            .build()
    }

    @Provides
    @Singleton
    fun givePullDao(db: AppDataBase): PullDao = db.pullDao()
}

//  Repository module
@Module
class RepositoryModule {
    @Provides
    fun giveGPRepo(): AppRepository = AppRepository()
}