package com.audhil.medium.samplegithubapp.rx

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable


//  Flowable
fun <T> Flowable<T>.makeFlowableRxConnection(iRxListeners: IRxListeners<T>, tag: String): Disposable =
        this.compose(AppRxSchedulers.applyFlowableSchedulers())
                .subscribeWith(AppRxDisposableSubscriber(iRxListeners, tag))

//  Observable
fun <T> Observable<T>.makeObservableRxConnection(iRxListeners: IRxListeners<T>, tag: String): Disposable =
        this.compose(AppRxSchedulers.applySchedulers())
                .subscribeWith(AppRxDisposableObserver(iRxListeners, tag))

//  Single
fun <T> Single<T>.makeSingleRxConnection(iRxListeners: IRxListeners<T>, tag: String): Disposable =
        this.compose(AppRxSchedulers.applySingleSchedulers())
                .subscribeWith(AppRxDisposableSingleObserver(iRxListeners, tag))