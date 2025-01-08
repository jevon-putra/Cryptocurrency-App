package com.jop.cointracker.network.api

import com.jop.cointracker.data.model.BaseResponse
import com.jop.cointracker.data.model.Coin
import com.jop.cointracker.data.model.CoinData
import com.jop.cointracker.data.model.CoinDetailData
import com.jop.cointracker.data.model.CoinHistoryPriceData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinAPI {
    @GET("v2/coins")
    suspend fun getCoins(@Query("limit") limit: Int,
                         @Query("offset") offset: Int,
                         @Query("symbols[]") symbols: List<String>,
                         @Query("tags[]") tags: List<String>) : BaseResponse<CoinData>

    @GET("v2/coin/{uuid}")
    suspend fun getCoinDetail(@Path("uuid") uuid: String) : BaseResponse<CoinDetailData>

    @GET("v2/coin/{uuid}/history")
    suspend fun getCoinHistoryPrice(@Path("uuid") uuid: String,
                                    @Query("timePeriod") timePeriod: String) : BaseResponse<CoinHistoryPriceData>
}