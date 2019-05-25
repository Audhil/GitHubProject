package com.audhil.medium.samplegithubapp

import android.app.Application
import com.audhil.medium.samplegithubapp.data.AppAPIs
import com.audhil.medium.samplegithubapp.di.components.DaggerGitHubAppComponent
import com.audhil.medium.samplegithubapp.di.components.GitHubAppComponent
import com.audhil.medium.samplegithubapp.di.modules.ApplicationModule
import com.audhil.medium.samplegithubapp.rx.AppRxSchedulers
import com.audhil.medium.samplegithubapp.util.showVLog
import io.reactivex.subscribers.DisposableSubscriber
import okhttp3.ResponseBody
import javax.inject.Inject

class SampleGitHubApp : Application() {

    companion object {
        lateinit var sINSTANCE: SampleGitHubApp
    }

    lateinit var appDaggerComponent: GitHubAppComponent
        private set

    @Inject
    lateinit var apis: AppAPIs

    override fun onCreate() {
        super.onCreate()
        sINSTANCE = this
        appDaggerComponent = DaggerGitHubAppComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        appDaggerComponent.inject(this)

        apis.getPullRequests("octocat", "Hello-World", "0")
            .compose(AppRxSchedulers.applyFlowableSchedulers())
            .subscribeWith(object : DisposableSubscriber<ResponseBody>() {
                override fun onComplete() {
                    showVLog("onComplete()")
                }

                override fun onNext(t: ResponseBody?) {
                    showVLog("onNext() response: ${t?.string()}")
                }

                override fun onError(t: Throwable?) {
                    showVLog("onError()")
                }
            })
    }
}