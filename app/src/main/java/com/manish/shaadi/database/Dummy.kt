package com.manish.shaadi.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "dummy")
class Dummy {

    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    var id : Int = 0
}