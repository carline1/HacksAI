package com.example.hacksai.api.services

import com.example.hacksai.api.models.res.ShipImageResponse
import com.example.hacksai.api.models.res.ShipResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MainService {

    @GET("getships")
    fun getShips(): Single<ShipResponse>

    @GET("getships")
    fun getImage(@Query("id") id: String): Single<ShipImageResponse>
}