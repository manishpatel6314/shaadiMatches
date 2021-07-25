package com.manish.shaadi.shaadimatch.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.manish.shaadi.helper.Logger

@Dao
interface MatchDTO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMatches(matches: List<MatchData>)

    @Transaction
    fun insertMatchesInTable(matches: List<MatchData>) {
        matches.map {
            it.username = it.login?.username ?: "-1"
            Logger.e(Thread.currentThread(), "${it.username}")
        }

        Logger.e(Thread.currentThread(), "$matches")
        insertMatches(matches)
    }

    @Query("SELECT * FROM matchDataTable")
    fun fetchMatches(): LiveData<List<MatchData>>

    @Query("UPDATE matchDataTable set status =:status where username = :username")
    fun updateMatchStatus(status: String, username: String)
}