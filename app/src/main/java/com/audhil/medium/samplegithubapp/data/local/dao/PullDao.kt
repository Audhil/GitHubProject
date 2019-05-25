package com.audhil.medium.samplegithubapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.audhil.medium.samplegithubapp.data.local.db.AppDBNames
import com.audhil.medium.samplegithubapp.data.model.db.PullEntity

@Dao
abstract class PullDao : BaseDao() {

    @Query("DELETE FROM " + AppDBNames.PULL_TABLE_NAME)
    abstract override fun deleteTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPullList(feeds: ArrayList<PullEntity>): List<Long>

    @Query("select * from " + AppDBNames.PULL_TABLE_NAME)
    abstract fun getPullList(): LiveData<List<PullEntity>>
}