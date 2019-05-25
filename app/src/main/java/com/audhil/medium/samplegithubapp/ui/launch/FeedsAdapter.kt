package com.audhil.medium.samplegithubapp.ui.launch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.audhil.medium.samplegithubapp.data.model.db.PullEntity
import com.audhil.medium.samplegithubapp.databinding.EmptyItemBinding
import com.audhil.medium.samplegithubapp.databinding.FeedItemBinding
import com.audhil.medium.samplegithubapp.databinding.LoadingItemBinding
import com.audhil.medium.samplegithubapp.ui.other.empty.EmptyViewHolder
import com.audhil.medium.samplegithubapp.ui.other.loading.LoadingItemViewHolder

class FeedsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var feeds = mutableListOf<PullEntity?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewTypes.LOADING ->
                LoadingItemViewHolder(LoadingItemBinding.inflate(LayoutInflater.from(parent.context)))  //  loading_item.xml

            ViewTypes.EMPTY ->
                EmptyViewHolder(EmptyItemBinding.inflate(LayoutInflater.from(parent.context)))  //  empty_item.xml

            else ->
                FeedsViewHolder(FeedItemBinding.inflate(LayoutInflater.from(parent.context)))   //  feed_item.xml
        }

    override fun getItemCount(): Int = feeds.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder) {
            is FeedsViewHolder ->
                holder.bindTo(feeds[position]!!)

            else ->
                Unit
        }

    override fun getItemViewType(position: Int): Int =
        when {
            feeds[position] == null ->
                ViewTypes.LOADING
            feeds.size == 0 ->
                ViewTypes.EMPTY
            else ->
                ViewTypes.FEED
        }

    fun addFeeds(it: MutableList<PullEntity>) {
        feeds.clear()
        feeds.addAll(it)
        feeds.add(null)
        notifyDataSetChanged()
    }
}

//  ViewTypes
object ViewTypes {
    const val LOADING = 0
    const val FEED = 1
    const val EMPTY = 2
}


//  feed_item.xml
class FeedsViewHolder(
    private val feedItemBinding: FeedItemBinding
) : RecyclerView.ViewHolder(feedItemBinding.root) {

    fun bindTo(pullEntity: PullEntity) {
        feedItemBinding.pEntity = pullEntity
        feedItemBinding.executePendingBindings()
    }
}