package com.audhil.medium.samplegithubapp.repository

import com.audhil.medium.samplegithubapp.GitHubDelegate
import com.audhil.medium.samplegithubapp.rx.IRxListeners
import com.audhil.medium.samplegithubapp.util.showELog
import com.audhil.medium.samplegithubapp.util.showVLog

abstract class BaseRepository : IRxListeners<Any> {

    init {
        GitHubDelegate.INSTANCE.appDaggerComponent.inject(this)
    }

    override fun onSuccess(obj: Any?, tag: String) = showVLog("onSuccess() :: $tag")

    override fun onSocketTimeOutException(t: Throwable?, tag: String) {
        showELog("onSocketTimeOutException :: + tag :" + tag + " :: t?.message :: " + t?.message)
    }

    override fun onUnknownHostException(t: Throwable?, tag: String) {
        showELog("onUnknownHostException :: + tag :" + tag + " :: t?.message :: " + t?.message)
    }

    override fun onIllegalArgumentException(t: Throwable?, tag: String) {
        showELog("onIllegalArgumentException :: + tag :" + tag + " :: t?.message :: " + t?.message)
    }

    override fun onUnKnownException(t: Throwable?, tag: String) {
        showELog("onUnKnownException :: + tag :" + tag + " :: t?.message :: " + t?.message)
    }

    override fun onComplete(tag: String) {
        showELog("onComplete() :: tag :$tag")
    }
}