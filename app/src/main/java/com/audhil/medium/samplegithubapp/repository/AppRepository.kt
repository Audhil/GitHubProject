package com.audhil.medium.samplegithubapp.repository

import androidx.lifecycle.LiveData
import com.audhil.medium.samplegithubapp.data.model.api.GitHubResponse
import com.audhil.medium.samplegithubapp.data.model.db.PullEntity
import com.audhil.medium.samplegithubapp.rx.makeFlowableRxConnection
import com.audhil.medium.samplegithubapp.util.ConstantsUtil
import io.reactivex.disposables.Disposable

//import okhttp3.ResponseBody

class AppRepository : BaseRepository() {

    //  fetch from db
    fun getPullFeedsFromDB(): LiveData<List<PullEntity>>? = dao.getPullList()

    fun fetchFromServer(userName: String, userRepo: String, page: String = ConstantsUtil.ZERO): Disposable =
        appAPIs.getPullRequests(userName, userRepo, page)
            .makeFlowableRxConnection(this, ConstantsUtil.FEEDS)

    override fun onSuccess(obj: Any?, tag: String) {
        when (tag) {
            ConstantsUtil.FEEDS -> {
                (obj as? GitHubResponse)?.let {
                    println("---outch it is ${it.issueNumber}")
                    println("---outch it is ${it.state}")
                    println("---outch it is ${it.closedAt}")
                    println("---outch it is ${it.user?.avatarUrl}")
                    println("---outch it is ${it.user?.userName}")
                }
            }
            else ->
                Unit
        }
    }
}