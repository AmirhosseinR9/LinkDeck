package com.smartlinksaver.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smartlinksaver.data.local.dao.GroupDao
import com.smartlinksaver.data.local.dao.LinkItemDao
import com.smartlinksaver.data.local.entity.Group
import com.smartlinksaver.data.local.entity.LinkItem

@Database(
    entities    = [LinkItem::class, Group::class],
    version     = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun linkItemDao(): LinkItemDao
    abstract fun groupDao(): GroupDao

    companion object {
        private const val DATABASE_NAME = "link_deck.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build().also { INSTANCE = it }
            }
    }
}
