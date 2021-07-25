package com.manish.shaadi.di.module

import android.app.Application
import androidx.room.Room
import com.manish.shaadi.database.Database
import com.manish.shaadi.helper.PrefManager
import com.manish.shaadi.shaadimatch.data.MatchDTO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): Database {
        return Room.databaseBuilder(
            application,
            Database::class.java, "database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePrefManager(application: Application): PrefManager {
        return PrefManager.getInstance(application)
    }

    @Provides
    @Singleton
    fun provideMatchDTO(db : Database): MatchDTO{
        return db.provideMatchDTO()
    }

}