package com.audhil.medium.samplegithubapp.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.audhil.medium.samplegithubapp.GitHubDelegate
import com.audhil.medium.samplegithubapp.repository.AppRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {


    @Inject
    lateinit var appRepository: AppRepository

    init {
        GitHubDelegate.INSTANCE.appDaggerComponent.inject(this)
    }

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}