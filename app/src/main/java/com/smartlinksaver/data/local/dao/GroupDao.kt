package com.smartlinksaver.data.local.dao

import androidx.room.*
import com.smartlinksaver.data.local.entity.Group
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Upsert
    suspend fun upsert(group: Group)

    @Delete
    suspend fun delete(group: Group)

    @Query("SELECT * FROM groups ORDER BY groupName ASC")
    fun getAllGroups(): Flow<List<Group>>
}
