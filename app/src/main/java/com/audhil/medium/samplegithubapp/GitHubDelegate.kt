package com.audhil.medium.samplegithubapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.inputmethod.InputMethodManager
import com.audhil.medium.samplegithubapp.di.components.DaggerGitHubAppComponent
import com.audhil.medium.samplegithubapp.di.components.GitHubAppComponent
import com.audhil.medium.samplegithubapp.di.modules.ApplicationModule
import javax.inject.Inject

class GitHubDelegate : Application() {

    companion object {
        lateinit var INSTANCE: GitHubDelegate
    }

    lateinit var appDaggerComponent: GitHubAppComponent
        private set

    @Inject
    lateinit var sPreferences: SharedPreferences

    @Inject
    lateinit var sPreferencesEditor: SharedPreferences.Editor

    var inputMethodManager: InputMethodManager? = null

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        appDaggerComponent = DaggerGitHubAppComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        appDaggerComponent.inject(this)
    }
}