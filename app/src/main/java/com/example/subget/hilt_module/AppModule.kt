package com.example.subget.hilt_module

import android.content.Context
import com.example.subget.app_data.local_db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Will provide the database to the Database interface constructor below
    @Provides
    @Singleton
    fun provideLocalDataBase(@ApplicationContext appContext : Context) : AppDatabase =
        AppDatabase.getInstance(appContext)

    // Will provide the database interface to our future repository's constructor
    @Provides
    @Singleton
    fun provideDatabaseDao(database: AppDatabase) = database.appDatabaseDao()
}