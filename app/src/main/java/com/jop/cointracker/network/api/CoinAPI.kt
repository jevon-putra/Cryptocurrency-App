package com.jop.cointracker.network.api

import com.jop.cointracker.data.model.BaseResponse
import com.jop.cointracker.data.model.Coin
import com.jop.cointracker.data.model.CoinData
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinAPI {
    @GET("v2/coins")
    suspend fun getCoins(@Query("limit") limit: Int,
                         @Query("offset") offset: Int,
                         @Query("symbols[]") symbols: List<String>,
                         @Query("tags[]") tags: List<String>) : BaseResponse<CoinData>
}