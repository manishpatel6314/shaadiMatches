package com.manish.shaadi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manish.shaadi.shaadimatch.data.MatchDTO
import com.manish.shaadi.shaadimatch.data.MatchData


@Database(
    entities = [
        Dummy::class,
        MatchData::class
    ], version = 1, exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun provideMatchDTO(): MatchDTO

}

