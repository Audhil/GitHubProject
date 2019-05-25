package com.audhil.medium.samplegithubapp.data.model.db

import android.os.Parcel
import android.os.Parcelable
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
    var id: Long = 0,
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
) : Parcelable {

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

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PullEntity> = object : Parcelable.Creator<PullEntity> {
            override fun createFromParcel(source: Parcel): PullEntity = PullEntity(source)
            override fun newArray(size: Int): Array<PullEntity?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readLong(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(index)
        writeLong(id)
        writeString(userName)
        writeString(avatarUrl)
        writeValue(issueNo)
        writeString(title)
        writeString(state)
        writeString(createdAt)
        writeString(closedAt)
    }
}