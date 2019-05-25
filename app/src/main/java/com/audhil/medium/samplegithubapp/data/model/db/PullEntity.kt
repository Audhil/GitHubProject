package com.audhil.medium.samplegithubapp.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.audhil.medium.samplegithubapp.data.local.db.AppDBNames
import com.audhil.medium.samplegithubapp.data.local.db.PullTable

@Entity(
    indices = [(Index(value = [PullTable.ID], unique = true))],
    tableName = AppDBNames.PULL_TABLE_NAME
)
class PullEntity(
    @PrimaryKey(autoGenerate = true)
    var index: Int = 0,
    @ColumnInfo(name = PullTable.ID)
    var id: Int = 0,
    @ColumnInfo(name = PullTable.USER_NAME)
    var userName: String? = null,
    @ColumnInfo(name = PullTable.AVATAR_URL)
    var avatarUrl: String? = null,
    @ColumnInfo(name = PullTable.ISSUE_NO)
    var issueNo: Int? = null,
    @ColumnInfo(name = PullTable.TITLE)
    var title: String? = null,
    @ColumnInfo(name = PullTable.STATE)
    var state: String? = null,
    @ColumnInfo(name = PullTable.CREATED_AT)
    var createdAt: String? = null,
    @ColumnInfo(name = PullTable.CLOSED_AT)
    var closedAt: String? = null
) {
    override fun toString(): String {
        return "PullEntity(index=$index, " +
                "id=$id, " +
                "userName=$userName, " +
                "avatarUrl=$avatarUrl, " +
                "issueNo=$issueNo, " +
                "title=$title, " +
                "state=$state, " +
                "createdAt=$createdAt, " +
                "closedAt=$closedAt)"
    }
}