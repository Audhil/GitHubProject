package com.audhil.medium.samplegithubapp.ui.other.pagination

import com.audhil.medium.samplegithubapp.util.GLog

abstract class EndlessRecyclerViewScrollListener(private val layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager) :
    androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
    //  minimum items to have below current scroll position, before loading more
    private var visibleThreshold: Int = 5
    // The current offset index of data you have loaded
    private var currentPage = 0
    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0
    // True if we are still waiting for the last set of data to load.
    private var loading = true
    // Sets the starting page index
    private var startingPageIndex = 0

    init {
        when (layoutManager) {
            is androidx.recyclerview.widget.LinearLayoutManager -> {
                GLog.v(javaClass.name, "")
            }
            is androidx.recyclerview.widget.GridLayoutManager -> {
                visibleThreshold *= layoutManager.spanCount
            }
            is androidx.recyclerview.widget.StaggeredGridLayoutManager -> {
                visibleThreshold *= layoutManager.spanCount
            }
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0)
                maxSize = lastVisibleItemPositions[i]
            else if (lastVisibleItemPositions[i] > maxSize)
                maxSize = lastVisibleItemPositions[i]
        }
        return maxSize
    }

    override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager.itemCount
        if (layoutManager is androidx.recyclerview.widget.StaggeredGridLayoutManager) {
            val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
        } else if (layoutManager is androidx.recyclerview.widget.GridLayoutManager) {
            lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        } else if (layoutManager is androidx.recyclerview.widget.LinearLayoutManager) {
            lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, recyclerView)
            loading = true
        }
    }

    // Call this method whenever performing new searches
    fun resetState() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: androidx.recyclerview.widget.RecyclerView)
}