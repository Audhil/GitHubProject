package com.audhil.medium.samplegithubapp

import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.audhil.medium.samplegithubapp.data.local.dao.PullDao
import com.audhil.medium.samplegithubapp.data.remote.AppAPIs
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
    lateinit var apis: AppAPIs

    @Inject
    lateinit var pullDao: PullDao

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

//        apis.getPullRequests("octocat", "Hello-World", "0")
//            .compose(AppRxSchedulers.applyFlowableSchedulers())
//            .subscribeWith(object : DisposableSubscriber<ResponseBody>() {
//                override fun onComplete() {
//                    showVLog("onComplete()")
//                }
//
//                override fun onNext(t: ResponseBody?) {
//                    showVLog("onNext() response: ${t?.string()}")
//                }
//
//                override fun onError(t: Throwable?) {
//                    showVLog("onError()")
//                }
//            })
    }
}