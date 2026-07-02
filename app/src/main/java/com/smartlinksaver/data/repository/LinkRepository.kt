package com.smartlinksaver.data.repository

import com.smartlinksaver.data.local.dao.GroupDao
import com.smartlinksaver.data.local.dao.LinkItemDao
import com.smartlinksaver.data.local.entity.Group
import com.smartlinksaver.data.local.entity.LinkItem
import com.smartlinksaver.data.model.WebMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkRepository @Inject constructor(
    private val linkItemDao: LinkItemDao,
    private val groupDao   : GroupDao
) {

    fun getAllLinks(): Flow<List<LinkItem>> = linkItemDao.getAllLinks()

    fun getLinksByGroup(groupId: Long): Flow<List<LinkItem>> =
        linkItemDao.getLinksByGroup(groupId)

    fun getAllGroups(): Flow<List<Group>> = groupDao.getAllGroups()

    suspend fun upsertLink(linkItem: LinkItem) = linkItemDao.upsert(linkItem)

    suspend fun deleteLink(linkItem: LinkItem) = linkItemDao.delete(linkItem)

    suspend fun upsertGroup(group: Group) = groupDao.upsert(group)

    suspend fun deleteGroup(group: Group) = groupDao.delete(group)

    suspend fun fetchWebMetadata(url: String): WebMetadata = withContext(Dispatchers.IO) {
        runCatching {
            val doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(8_000)
                .get()

            val title = doc.select("meta[property=og:title]").attr("content")
                .ifBlank { doc.title() }
                .ifBlank { null }

            val description = doc.select("meta[property=og:description]").attr("content")
                .ifBlank { doc.select("meta[name=description]").attr("content") }
                .ifBlank { null }

            val imageUrl = doc.select("meta[property=og:image]").attr("content")
                .ifBlank { null }

            WebMetadata(title, description, imageUrl)
        }.getOrElse {
            WebMetadata(null, null, null)
        }
    }
}
