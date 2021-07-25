package com.manish.shaadi.shaadimatch.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "matchDataTable")
data class MatchData(
    @field:SerializedName("gender")
    var gender: String? = null,

    @field:SerializedName("location")
    var location: LocationCLass? = null,

    @field:SerializedName("name")
    var name: MatchNamedClass? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("login")
    var login: LoginClass? = null,

    @field:SerializedName("dob")
    var dob: DobClass? = null,

    @field:SerializedName("registered")
    var registered: RegisteredClass? = null,

    @field:SerializedName("phone")
    var phone: String? = null,

    @field:SerializedName("cell")
    var cell: String? = null,

    @field:SerializedName("id")
    var id: IdClass? = null,

    @field:SerializedName("picture")
    var picture: PictureClass? = null,

    @field:SerializedName("nat")
    var nat: String? = null,

    @PrimaryKey
    @field:SerializedName("userName")
    var username: String = "-1",

    var status: String? = null
) {

    fun getPersonalDetails(): String {
        return this.dob.toString() + " yrs, $phone"
    }

    fun getStatusValue(): String? {
        return when (status) {
            "accept" -> "Member Accepted"
            "decline" -> "Member Decline"
            else -> null
        }
    }
}

data class LocationCLass(
    @field:SerializedName("street")
    var street: StreetClass? = null,

    @field:SerializedName("city")
    var city: String? = null,

    @field:SerializedName("state")
    var state: String? = null,

    @field:SerializedName("country")
    var country: String? = null,

    @field:SerializedName("postcode")
    var postcode: Int? = null,

    @field:SerializedName("coordinates")
    var coordinates: CoordinatesClass? = null,

    @field:SerializedName("timezone")
    var timezone: TimeZoneClass? = null
) {
    override fun toString(): String {
        return "$city, $state, $country"
    }
}

data class MatchNamedClass(
    @field:SerializedName("title")
    var title: String = "",

    @field:SerializedName("first")
    var firstName: String = "",

    @field:SerializedName("last")
    var lastName: String = ""
) {
    override fun toString(): String {
        return "$title $firstName $lastName"
    }
}

data class StreetClass(
    @field:SerializedName("number")
    var number: Int? = null,

    @field:SerializedName("name")
    var name: String? = null
)

data class CoordinatesClass(
    @field:SerializedName("latitude")
    var latitude: String? = null,

    @field:SerializedName("longitude")
    var longitude: String? = null
)

data class TimeZoneClass(
    @field:SerializedName("offset")
    var offset: String? = null,

    @field:SerializedName("description")
    var description: String? = null
)

data class LoginClass(
    @field:SerializedName("uuid")
    var uuid: String? = null,

    @field:SerializedName("username")
    var username: String? = null,

    @field:SerializedName("password")
    var password: String? = null,

    @field:SerializedName("salt")
    var salt: String? = null,

    @field:SerializedName("md5")
    var md5: String? = null,

    @field:SerializedName("sha1")
    var sha1: String? = null,

    @field:SerializedName("sha256")
    var sha256: String? = null
)

data class DobClass(
    @field:SerializedName("date")
    var date: String? = null,

    @field:SerializedName("age")
    var age: Int? = null
) {
    override fun toString(): String {
        return if (age == null) "0" else "$age"
    }
}

data class RegisteredClass(
    @field:SerializedName("date")
    var date: String? = null,

    @field:SerializedName("age")
    var age: Int? = null
)

data class IdClass(
    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("value")
    var value: String? = null
)

data class PictureClass(
    @field:SerializedName("large")
    var large: String? = null,

    @field:SerializedName("medium")
    var medium: String? = null,

    @field:SerializedName("thumbnail")
    var thumbnail: String? = null
)