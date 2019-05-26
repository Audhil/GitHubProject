package com.audhil.medium.samplegithubapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.audhil.medium.samplegithubapp.GitHubDelegate
import com.audhil.medium.samplegithubapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("bind:imgURLToLoad")
fun imgToLoad(imageView: ImageView, imgUrl: String) =
    Glide.with(GitHubDelegate.INSTANCE)
        .load(imgUrl)
        .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round))
        .into(imageView)