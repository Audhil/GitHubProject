package com.audhil.medium.samplegithubapp.ui.launch

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import com.audhil.medium.samplegithubapp.data.model.db.PullEntity
import com.audhil.medium.samplegithubapp.ui.base.BaseViewModel
import com.audhil.medium.samplegithubapp.util.ConstantsUtil

class LaunchViewModel(application: Application) : BaseViewModel(application) {

    //  fetching from server
    val feedsLiveData = MediatorLiveData<MutableList<PullEntity>>().apply {
        addSource(appRepository.dao.getPullList()) {
            value = it
        }
    }

    //  fetch from server
    fun fetchFromServer(userName: String, userRepo: String, page: String = ConstantsUtil.ZERO) =
        compositeDisposable.add(
            appRepository.fetchFromServer(userName, userRepo, page)
        )

    //  delete table
    fun deleteTable() =
        appRepository.appExecutors.diskIOThread().execute {
            appRepository.dao.deleteTable()
        }
}