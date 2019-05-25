package com.audhil.medium.samplegithubapp.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.audhil.medium.samplegithubapp.GitHubDelegate
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

//    @Inject
//    lateinit var gpRepository: GeneralPurposeRepository

    init {
        GitHubDelegate.INSTANCE.appDaggerComponent.inject(this)
    }

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}