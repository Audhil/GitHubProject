package com.audhil.medium.samplegithubapp.runner

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import com.audhil.medium.samplegithubapp.UiGitHubDelegate
import android.os.StrictMode

open class UiRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, UiGitHubDelegate::class.java.name, context)
    }
}