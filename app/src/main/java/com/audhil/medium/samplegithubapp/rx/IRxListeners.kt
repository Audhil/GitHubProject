package com.audhil.medium.samplegithubapp.rx

interface IRxListeners<in T> {
    fun onSuccess(obj: T?, tag: String)
    fun onUnknownHostException(t: Throwable?, tag: String)
    fun onIllegalArgumentException(t: Throwable?, tag: String)
    fun onUnKnownException(t: Throwable?, tag: String)
    fun onSocketTimeOutException(t: Throwable?, tag: String)
    fun onComplete(tag: String)
}