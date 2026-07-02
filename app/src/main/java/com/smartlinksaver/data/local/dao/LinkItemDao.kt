package com.smartlinksaver.data.local.dao

import androidx.room.*
import com.smartlinksaver.data.local.entity.LinkItem
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkItemDao {

    @Upsert
    suspend fun upsert(linkItem: LinkItem)

    @Delete
    suspend fun delete(linkItem: LinkItem)

    @Query("SELECT * FROM link_items ORDER BY id DESC")
    fun getAllLinks(): Flow<List<LinkItem>>

    @Query("SELECT * FROM link_items WHERE groupId = :groupId ORDER BY id DESC")
    fun getLinksByGroup(groupId: Long): Flow<List<LinkItem>>
}
