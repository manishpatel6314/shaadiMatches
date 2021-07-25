package com.manish.shaadi.remoteUtils

import com.google.gson.annotations.SerializedName

data class ApiStatus(

    @SerializedName("health")
    var health: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("version")
    var version: String? = null
)