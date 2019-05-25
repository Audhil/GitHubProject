package com.audhil.medium.samplegithubapp.repository

import com.audhil.medium.samplegithubapp.GitHubDelegate
import com.audhil.medium.samplegithubapp.data.local.dao.PullDao
import com.audhil.medium.samplegithubapp.data.model.api.AppExecutors
import com.audhil.medium.samplegithubapp.data.model.api.ErrorLiveData
import com.audhil.medium.samplegithubapp.data.model.api.NetworkError
import com.audhil.medium.samplegithubapp.data.remote.AppAPIs
import com.audhil.medium.samplegithubapp.rx.IRxListeners
import com.audhil.medium.samplegithubapp.util.showELog
import com.audhil.medium.samplegithubapp.util.showVLog
import com.google.gson.Gson
import javax.inject.Inject

abstract class BaseRepository : IRxListeners<Any> {

    @Inject
    lateinit var appAPIs: AppAPIs
    @Inject
    lateinit var dao: PullDao
    @Inject
    lateinit var errorLiveData: ErrorLiveData
    @Inject
    lateinit var gson: Gson
    @Inject
    lateinit var appExecutors: AppExecutors

    init {
        GitHubDelegate.INSTANCE.appDaggerComponent.inject(this)
    }

    override fun onSuccess(obj: Any?, tag: String) = showVLog("onSuccess() :: $tag")

    override fun onSocketTimeOutException(t: Throwable?, tag: String) {
        showELog("onSocketTimeOutException :: + tag :" + tag + " :: t?.message :: " + t?.message)
        errorLiveData.setNetworkError(NetworkError.SOCKET_TIMEOUT)
    }

    override fun onUnknownHostException(t: Throwable?, tag: String) {
        showELog("onUnknownHostException :: + tag :" + tag + " :: t?.message :: " + t?.message)
        errorLiveData.setNetworkError(NetworkError.DISCONNECTED)
    }

    override fun onIllegalArgumentException(t: Throwable?, tag: String) {
        showELog("onIllegalArgumentException :: + tag :" + tag + " :: t?.message :: " + t?.message)
        errorLiveData.setNetworkError(NetworkError.BAD_URL)
    }

    override fun onUnKnownException(t: Throwable?, tag: String) {
        showELog("onUnKnownException :: + tag :" + tag + " :: t?.message :: " + t?.message)
        errorLiveData.setNetworkError(NetworkError.UNKNOWN)
    }

    override fun onComplete(tag: String) {
        showELog("onComplete() :: tag :$tag")
    }
}