package com.audhil.medium.samplegithubapp.repository

import androidx.lifecycle.LiveData
import com.audhil.medium.samplegithubapp.data.model.api.GitHubResponse
import com.audhil.medium.samplegithubapp.data.model.db.PullEntity
import com.audhil.medium.samplegithubapp.rx.makeFlowableRxConnection
import com.audhil.medium.samplegithubapp.util.CallBack
import com.audhil.medium.samplegithubapp.util.ConstantsUtil
import com.google.gson.reflect.TypeToken
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody

class AppRepository : BaseRepository() {

    var listEndCallBack: CallBack<Unit>? = null
    private val pullEntityList = mutableListOf<PullEntity>()

    //  fetch from db
    fun getPullFeedsFromDB(): LiveData<MutableList<PullEntity>> = dao.getPullList()

    fun fetchFromServer(userName: String, userRepo: String, page: String = ConstantsUtil.ZERO): Disposable =
        appAPIs.getPullRequests(userName, userRepo, page)
            .makeFlowableRxConnection(this, ConstantsUtil.FEEDS)

    override fun onSuccess(obj: Any?, tag: String) {
        when (tag) {
            ConstantsUtil.FEEDS ->
                (obj as? ResponseBody)?.let {
                    val pullList: ArrayList<GitHubResponse> =
                        gson.fromJson((it.string()), object : TypeToken<ArrayList<GitHubResponse>>() {}.type)

                    //  reached end
                    if (pullList.size == 0)
                        listEndCallBack?.invoke(Unit)

                    pullEntityList.clear()
                    pullList.forEach { response ->
                        pullEntityList.add(
                            PullEntity(
                                id = response.id,
                                userName = response.user?.userName,
                                avatarUrl = response.user?.avatarUrl,
                                issueNo = response.issueNumber?.toInt(),
                                title = response.title,
                                state = response.state,
                                createdAt = response.createdAt,
                                closedAt = response.closedAt
                            )
                        )
                    }
                    //  insert into DB
                    appExecutors.diskIOThread().execute {
                        dao.insertPullList(feeds = pullEntityList)
                    }
                }

            else ->
                Unit
        }
    }
}