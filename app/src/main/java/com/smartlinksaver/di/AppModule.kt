package com.smartlinksaver.di

import android.content.Context
import com.smartlinksaver.data.local.AppDatabase
import com.smartlinksaver.data.local.dao.GroupDao
import com.smartlinksaver.data.local.dao.LinkItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Provides
    fun provideLinkItemDao(db: AppDatabase): LinkItemDao = db.linkItemDao()

    @Provides
    fun provideGroupDao(db: AppDatabase): GroupDao = db.groupDao()
}
