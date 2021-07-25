package com.manish.shaadi.remoteUtils

import androidx.lifecycle.LiveData
import com.manish.shaadi.shaadimatch.data.MatchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WebService {

    @GET
    fun fetch(@Url url : String,@Query ("result") result : Int): LiveData<ApiResponse<MatchResponse>>
}