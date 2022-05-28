package com.example.subget.hilt_module

import android.content.Context
import com.example.subget.app_data.local_db.AppDatabase
import com.example.subget.app_data.remote_db.ListingService
import com.example.subget.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides Gson for Retrofit
    @Provides
    fun provideGson() : Gson = GsonBuilder().create()

    // Provides Retrofit for ListingService
    @Provides
    @Singleton
    fun provideRetrofit(gson : Gson) : Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    // Provides ListingService for RemoteDataSource
    @Provides
    fun provideListingService(retrofit: Retrofit) : ListingService =
        retrofit.create(ListingService::class.java)

    // Provides AppDataBase for DatabaseDAO
    @Provides
    @Singleton
    fun provideLocalDataBase(@ApplicationContext appContext : Context) : AppDatabase =
        AppDatabase.getInstance(appContext)

    // Provides Database DAO for Repository
    @Provides
    @Singleton
    fun provideDatabaseDao(database: AppDatabase) = database.appDatabaseDao()
}