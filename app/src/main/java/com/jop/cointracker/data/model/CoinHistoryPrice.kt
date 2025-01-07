package com.jop.cointracker.data.model

import com.google.gson.annotations.SerializedName

data class CoinHistoryPrice(
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("timestamp")
    val timestamp: Int = 0
)


