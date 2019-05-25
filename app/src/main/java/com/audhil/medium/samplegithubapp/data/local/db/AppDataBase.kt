package com.audhil.medium.samplegithubapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.audhil.medium.samplegithubapp.data.local.dao.PullDao
import com.audhil.medium.samplegithubapp.data.local.db.AppDBNames.DB_NAME
import com.audhil.medium.samplegithubapp.data.model.db.PullEntity

@Database(
    entities = [
        (PullEntity::class)
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        const val dbName = DB_NAME
    }

    abstract fun pullDao(): PullDao
}

//  DB & TableNames
object AppDBNames {
    const val DB_NAME = "AppDataBase.db"
    const val PULL_TABLE_NAME = "PullTable"
}

//  JobsTable
object PullTable {
    const val ID = "id"
    const val USER_NAME = "userName"
    const val AVATAR_URL = "avatarUrl"
    const val ISSUE_NO = "iissueNo"
    const val TITLE = "title"
    const val STATE = "state"
    const val CREATED_AT = "createdAt"
    const val CLOSED_AT = "closedAt"
}