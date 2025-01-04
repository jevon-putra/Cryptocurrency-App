package com.jop.cointracker.network.api

import com.jop.cointracker.data.model.BaseResponse
import com.jop.cointracker.data.model.Coin
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinAPI {
    @GET("assets?limit=20")
    suspend fun getCoins(@Query("offset") offset: Int) : BaseResponse<List<Coin>>
}