package com.audhil.medium.samplegithubapp.util

import com.audhil.medium.samplegithubapp.data.model.db.PullEntity
import java.text.SimpleDateFormat
import java.util.*

object ConstantsUtil {
    const val DASH = "---"
    const val EMPTY = ""
    const val INVALID_INPUT = "invalid input"
    const val APP_PREF_NAME = "GitHub_Pref_Name"

    const val BLANK_SPACE = " "

    const val OWNER_NAME = "OwnerName"
    const val REPO_NAME = "RepoName"
    const val FORWARD_SLASH = "/"
    const val GITHUB_ENDPOINT = "https://api.github.com"
    const val REPOS_API = FORWARD_SLASH + "repos"
    const val PULLS = "pulls"
    const val PAGE = "page"
    const val FEEDS = "feeds"
    const val ZERO = "0"

    //    2018-10-18T15:18:27Z
    private val dateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
    }

    private val formatter by lazy {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    }

    //  make a line
    fun makeALine(pullEntity: PullEntity): String =
        if (pullEntity.closedAt == null)
            "#" + pullEntity.issueNo + BLANK_SPACE + "opened on" + BLANK_SPACE + formatter.format(
                dateFormat.parse(
                    pullEntity.createdAt
                )
            ) + " by " + pullEntity.userName
        else
            "#" + pullEntity.issueNo + " by " + pullEntity.userName + " was closed on " + formatter.format(
                dateFormat.parse(
                    pullEntity.createdAt
                )
            )
}