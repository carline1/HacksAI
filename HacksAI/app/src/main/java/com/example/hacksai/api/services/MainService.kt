package com.example.hacksai.api.services

import com.example.hacksai.api.models.res.ShipDayResponse
import com.example.hacksai.api.models.res.ShipImageResponse
import com.example.hacksai.api.models.res.ShipResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MainService {

    @GET("getships")
    fun getShips(): Single<List<ShipResponse>>

    @GET("getships")
    fun getImageById(@Query("id") id: String): Single<ShipImageResponse>

    @GET("getships")
    fun getImage(@QueryMap parameters: Map<String, String>): Single<List<ShipDayResponse>>
}