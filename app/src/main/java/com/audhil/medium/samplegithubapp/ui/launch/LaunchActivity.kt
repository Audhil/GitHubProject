package com.audhil.medium.samplegithubapp.ui.launch

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.audhil.medium.samplegithubapp.R
import com.audhil.medium.samplegithubapp.data.model.api.NetworkError
import com.audhil.medium.samplegithubapp.databinding.ActivityLaunchBinding
import com.audhil.medium.samplegithubapp.ui.base.BaseLifeCycleActivity
import com.audhil.medium.samplegithubapp.ui.other.pagination.EndlessRecyclerViewScrollListener
import com.audhil.medium.samplegithubapp.util.*
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : BaseLifeCycleActivity<ActivityLaunchBinding, LaunchViewModel>() {

    override val viewModelClass: Class<LaunchViewModel>
        get() = LaunchViewModel::class.java

    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.activity_launch

    override fun internetAvailable() =
        showVLog("internetAvailable")

    override fun internetUnAvailable() =
        showVLog("internetUnAvailable")

    private var downloadMenuItem: MenuItem? = null
    private var pageNo = 1
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private val feedsAdapter by lazy {
        FeedsAdapter()
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        scrollListener?.resetState()
        viewModel.deleteTable()
        pageNo = 1
        viewModel.fetchFromServer(
            userName = ConstantsUtil.OWNER_NAME.readStringFromPref(),
            userRepo = ConstantsUtil.REPO_NAME.readStringFromPref(),
            page = pageNo.toString()
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_launch, menu)
        downloadMenuItem = menu?.findItem(R.id.action_download)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_download ->
                //  showing alert dialog
                showDataAlertDialog {
                    //  for sample
                    ownerEditText.apply {
                        setText("octocat")
                        setSelection(ownerEditText.text.length)
                    }
                    repoEditText.apply {
                        setText("Hello-World")
                        setSelection(repoEditText.text.length)
                    }

                    goBtnClickListener {
                        if (!isNetworkConnected()) {
                            getString(R.string.no_internet).showToast()
                            return@goBtnClickListener
                        }
                        if (TextUtils.isEmpty(ownerEditText.text) || TextUtils.isEmpty(repoEditText.text)) {
                            getString(R.string.invalid_entries).showToast()
                            return@goBtnClickListener
                        }
                        ownerEditText.text.toString().writeToPref(ConstantsUtil.OWNER_NAME)
                        repoEditText.text.toString().writeToPref(ConstantsUtil.REPO_NAME)
                        refreshListener.onRefresh()
                        dialog?.dismiss()
                    }
                }.show()

            else ->
                Unit
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(base_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initErrorObserver()
        initViews()
        initDataObserver()

        //  click menu item if prior no valid data available
        viewDataBinding.root.postDelayed({
            if (
                ConstantsUtil.OWNER_NAME.readStringFromPref().equals(ConstantsUtil.EMPTY, true) ||
                ConstantsUtil.REPO_NAME.readStringFromPref().equals(ConstantsUtil.EMPTY, true)
            ) {
                onOptionsItemSelected(downloadMenuItem)
            }
        }, 100)
    }

    //  init views
    private fun initViews() {
        viewDataBinding.apply {
            swipeRefreshLayout.setOnRefreshListener(refreshListener)
            recyclerView.apply {
                adapter = feedsAdapter
                feedsAdapter.clickListener = { pos, item ->
                    "pos: $pos, item: ${item.avatarUrl}".showToast()
                }
                layoutManager?.let {
                    scrollListener = object : EndlessRecyclerViewScrollListener(it) {

                        override fun onLoadMore(
                            page: Int,
                            totalItemsCount: Int,
                            view: androidx.recyclerview.widget.RecyclerView
                        ) {
                            pageNo++
                            viewModel.fetchFromServer(
                                userName = ConstantsUtil.OWNER_NAME.readStringFromPref(),
                                userRepo = ConstantsUtil.REPO_NAME.readStringFromPref(),
                                page = pageNo.toString()
                            )
                        }
                    }
                }
                scrollListener?.let {
                    addOnScrollListener(it)
                }
            }
        }
    }

    private fun initErrorObserver() =
        viewModel.appRepository.errorLiveData.observe(this, Observer { networkError ->
            viewDataBinding.swipeRefreshLayout.isRefreshing = false
            when (networkError) {
                NetworkError.DISCONNECTED -> showVLog("Error : DISCONNECTED")
                NetworkError.BAD_URL -> showVLog("Error : BAD_URL")
                NetworkError.UNKNOWN -> showVLog("Error : UNKNOWN")
                NetworkError.SOCKET_TIMEOUT -> showVLog("Error : SOCKET_TIMEOUT")
                else -> Unit
            }
        })


    //  data observer
    private fun initDataObserver() {
        viewModel.feedsLiveData.observe(this, Observer {
            viewDataBinding.swipeRefreshLayout.isRefreshing = false
            feedsAdapter.addFeeds(it)
        })
    }
}