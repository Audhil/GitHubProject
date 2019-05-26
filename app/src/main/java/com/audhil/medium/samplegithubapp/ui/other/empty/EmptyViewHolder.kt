package com.audhil.medium.samplegithubapp.ui.other.empty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.audhil.medium.samplegithubapp.R
import com.audhil.medium.samplegithubapp.data.model.db.PullEntity

//  empty_item.xml
class EmptyViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.empty_item, parent, false))

//  empty item
class EmptyItem : PullEntity()