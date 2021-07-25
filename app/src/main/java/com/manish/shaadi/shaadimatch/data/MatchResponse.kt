package com.manish.shaadi.shaadimatch.data

import com.google.gson.annotations.SerializedName

class MatchResponse(

    @field:SerializedName("results")
    var results: List<MatchData>
)