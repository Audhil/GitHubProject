package com.audhil.medium.samplegithubapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.audhil.medium.samplegithubapp.GitHubDelegate
import com.audhil.medium.samplegithubapp.R
import com.audhil.medium.samplegithubapp.rx.AppRxSchedulers
import com.audhil.medium.samplegithubapp.util.ConstantsUtil
import com.audhil.medium.samplegithubapp.util.showVLog
import com.google.gson.annotations.SerializedName
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.android.synthetic.main.activity_dummy.*

class DummyActivity : AppCompatActivity() {

    private val mockUrl by lazy {
        intent?.extras?.getString(ConstantsUtil.MOCK_URL, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy)
        fetchFromServer("https://jsonplaceholder.typicode.com/todos/1")
    }

    //  make server query
    private fun fetchFromServer(url: String) {
        val disp = GitHubDelegate.INSTANCE.appAPIs.getSampleJson(mockUrl ?: url)
            .compose(AppRxSchedulers.applyFlowableSchedulers())
            .subscribeWith(object : DisposableSubscriber<SampleJson>() {
                override fun onComplete() {
                    showVLog("FFFF onComplete()")
                }

                override fun onNext(t: SampleJson?) {
                    showVLog("FFFF onNext() t?.completed: ${t?.completed}")
                    dddd.text = "success!!!"
                }

                override fun onError(t: Throwable?) {
                    showVLog("FFFF onError()")
                    dddd.text = "somethingWentWrong!!!"
                }
            })
    }
}

data class SampleJson(
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("completed")
    val completed: Boolean? = null
)
