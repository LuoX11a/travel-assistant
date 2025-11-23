package com.cp3406.smarttravelplanningassistant.di

import android.content.Context
import androidx.room.Room
import com.cp3406.smarttravelplanningassistant.data.local.AppDatabase
import com.cp3406.smarttravelplanningassistant.data.remote.TravelApiService
import com.cp3406.smarttravelplanningassistant.data.repository.TravelRepository
import com.cp3406.smarttravelplanningassistant.data.repository.TravelRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "travel_assistant.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTravelApiService(): TravelApiService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TravelApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTravelRepository(
        db: AppDatabase,
        api: TravelApiService
    ): TravelRepository {
        return TravelRepositoryImpl(
            tripDao = db.tripDao(),
            itineraryDao = db.itineraryDao(),
            expenseDao = db.expenseDao(),
            api = api
        )
    }
}
