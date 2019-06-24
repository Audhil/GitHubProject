package com.audhil.medium.samplegithubapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.inputmethod.InputMethodManager
import com.audhil.medium.samplegithubapp.data.remote.AppAPIs
import com.audhil.medium.samplegithubapp.di.components.DaggerGitHubAppComponent
import com.audhil.medium.samplegithubapp.di.components.GitHubAppComponent
import com.audhil.medium.samplegithubapp.di.modules.ApplicationModule
import com.facebook.stetho.Stetho
import javax.inject.Inject

open class GitHubDelegate : Application() {

    companion object {
        lateinit var INSTANCE: GitHubDelegate
    }

    open lateinit var appDaggerComponent: GitHubAppComponent

    @Inject
    lateinit var sPreferences: SharedPreferences

    @Inject
    lateinit var sPreferencesEditor: SharedPreferences.Editor

    @Inject
    lateinit var appAPIs: AppAPIs

    private var inputMethodManager: InputMethodManager? = null

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        appDaggerComponent = getAppComponent()
        appDaggerComponent.inject(this)
        Stetho.initializeWithDefaults(this)
    }

    //  returns app component
    open fun getAppComponent(): GitHubAppComponent =
        DaggerGitHubAppComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
}