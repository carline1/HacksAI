package com.example.hacksai.api.models.res


import com.google.gson.annotations.SerializedName
import java.io.ObjectInputStream

data class ShipImageResponse(
    @SerializedName("img")
    val img: String
)