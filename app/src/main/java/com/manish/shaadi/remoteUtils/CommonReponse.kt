package com.manish.shaadi.remoteUtils

import com.manish.shaadi.remoteUtils.ApiStatus
import com.google.gson.annotations.SerializedName

data class CommonReponse(

    @field:SerializedName("api")
    var apiStatus: ApiStatus? = null,

    @field:SerializedName("status")
    var status: String? = null,

    @field:SerializedName("statusCode")
    var statusCode: Int? = null
)