package com.manish.shaadi.database

import com.manish.shaadi.shaadimatch.data.*
import com.google.gson.Gson

class TypeConverter {

    @androidx.room.TypeConverter
    fun listToJson(value: MutableList<String?>?): String? {
        return Gson().toJson(value)
    }

    @androidx.room.TypeConverter
    fun jsonToList(value: String?): MutableList<String?>? {
        val objects = Gson().fromJson(value, Array<String?>::class.java)
        return objects?.toMutableList()
    }

    @androidx.room.TypeConverter
    fun locationDataToJson(value: LocationCLass): String? {
        return Gson().toJson(value)
    }

    @androidx.room.TypeConverter
    fun jsonToLocationData(value: String?): LocationCLass {
        return Gson().fromJson(value, LocationCLass::class.java)
    }

    @androidx.room.TypeConverter
    fun matchNamedClassToJson(value: MatchNamedClass): String? {
        return Gson().toJson(value)
    }

    @androidx.room.TypeConverter
    fun jsonToMatchNamedClass(value: String?): MatchNamedClass {
        return Gson().fromJson(value, MatchNamedClass::class.java)
    }

    @androidx.room.TypeConverter
    fun loginClassToJson(value: LoginClass): String? {
        return Gson().toJson(value)
    }

    @androidx.room.TypeConverter
    fun jsonToLoginClass(value: String?): LoginClass {
        return Gson().fromJson(value, LoginClass::class.java)
    }

    @androidx.room.TypeConverter
    fun dobClassToJson(value: DobClass): String? {
        return Gson().toJson(value)
    }

    @androidx.room.TypeConverter
    fun jsonToDobClass(value: String?): DobClass {
        return Gson().fromJson(value, DobClass::class.java)
    }

    @androidx.room.TypeConverter
    fun registeredClassToJson(value: RegisteredClass): String? {
        return Gson().toJson(value)
    }

    @androidx.room.TypeConverter
    fun jsonToRegisteredClass(value: String?): RegisteredClass {
        return Gson().fromJson(value, RegisteredClass::class.java)
    }

    @androidx.room.TypeConverter
    fun idClassToJson(value: IdClass): String? {
        return Gson().toJson(value)
    }

    @androidx.room.TypeConverter
    fun jsonToIdClass(value: String?): IdClass {
        return Gson().fromJson(value, IdClass::class.java)
    }

    @androidx.room.TypeConverter
    fun pictureClassToJson(value: PictureClass): String? {
        return Gson().toJson(value)
    }

    @androidx.room.TypeConverter
    fun jsonToPictureClass(value: String?): PictureClass {
        return Gson().fromJson(value, PictureClass::class.java)
    }

    @androidx.room.TypeConverter
    fun coordinatesClassToJson(value: CoordinatesClass): String? {
        return Gson().toJson(value)
    }

    @androidx.room.TypeConverter
    fun jsonToCoordinatesClass(value: String?): CoordinatesClass {
        return Gson().fromJson(value, CoordinatesClass::class.java)
    }

    @androidx.room.TypeConverter
    fun timeZoneClassToJson(value: TimeZoneClass): String? {
        return Gson().toJson(value)
    }

    @androidx.room.TypeConverter
    fun jsonToTimeZoneClass(value: String?): TimeZoneClass {
        return Gson().fromJson(value, TimeZoneClass::class.java)
    }
}