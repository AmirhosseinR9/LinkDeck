package com.smartlinksaver.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "link_items",
    foreignKeys = [
        ForeignKey(
            entity        = Group::class,
            parentColumns = ["id"],
            childColumns  = ["groupId"],
            onDelete      = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("groupId")]
)
data class LinkItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val url           : String,
    val title         : String?,
    val webDescription: String?,
    val imageUrl      : String?,
    val userNotes     : String?,
    val groupId       : Long?
)
