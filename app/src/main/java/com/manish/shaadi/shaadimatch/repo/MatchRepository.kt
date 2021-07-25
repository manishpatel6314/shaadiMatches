package com.manish.shaadi.shaadimatch.repo

import androidx.lifecycle.LiveData
import com.manish.shaadi.helper.AppExecutors
import com.manish.shaadi.helper.Logger
import com.manish.shaadi.remoteUtils.ApiResponse
import com.manish.shaadi.remoteUtils.NetworkBoundResource
import com.manish.shaadi.remoteUtils.Resource
import com.manish.shaadi.remoteUtils.WebService
import com.manish.shaadi.shaadimatch.data.MatchDTO
import com.manish.shaadi.shaadimatch.data.MatchData
import com.manish.shaadi.shaadimatch.data.MatchResponse
import com.google.gson.Gson
import javax.inject.Inject

class MatchRepository @Inject constructor(
    val webService: WebService,
    val dao: MatchDTO,
    val appExecutors: AppExecutors
) {

    fun fetchMatchesDetails(result: Int): LiveData<Resource<List<MatchData>>> {
        return object : NetworkBoundResource<List<MatchData>, MatchResponse>(appExecutors) {
            override fun saveCallResult(item: MatchResponse) {
                Logger.e(Thread.currentThread(), "item ${Gson().toJson(item)}")
                dao.insertMatchesInTable(item.results)
            }

            override fun shouldFetch(data: List<MatchData>?): Boolean =
                true

            override fun loadFromDb(): LiveData<List<MatchData>> {
                return dao.fetchMatches()
            }

            override fun createCall(): LiveData<ApiResponse<MatchResponse>> {
                return webService.fetch(url = "https://randomuser.me/api/", result = result)
            }

            override fun uploadTag(): String? = null

        }.asLiveData()
    }

    fun saveUserResponse(action: String, username: String) {
        appExecutors.diskIO().execute {
            dao.updateMatchStatus(action, username)
        }
    }
}