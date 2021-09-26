package com.example.hacksai.api.models.res

import com.google.gson.annotations.SerializedName
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.Advertisement

data class ShipDayResponse(
    @SerializedName("record")
    val day: String,
    val latitude: String,
    val longitude: String
)

fun ShipDayResponse.toPoint() = Point(latitude.toDouble(), longitude.toDouble())
fun ShipDayResponse.toList() = listOf(latitude.toDouble(), longitude.toDouble())
//fun List<ShipDayResponse>.min() =