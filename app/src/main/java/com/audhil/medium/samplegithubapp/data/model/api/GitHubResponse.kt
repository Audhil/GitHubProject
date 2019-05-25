package com.audhil.medium.samplegithubapp.data.model.api

import com.google.gson.annotations.SerializedName

data class GitHubResponse(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("number")
    val issueNumber: String? = null,
    @SerializedName("state")
    val state: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("closed_at")
    val closedAt: String? = null,
    @SerializedName("user")
    val user: User? = null
)

data class User(
    @SerializedName("login")
    val userName: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null
)