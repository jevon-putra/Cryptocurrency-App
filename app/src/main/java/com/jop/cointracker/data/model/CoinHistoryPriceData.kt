package com.jop.cointracker.data.model

import com.google.gson.annotations.SerializedName

data class CoinHistoryPriceData(
    @SerializedName("history")
    val history: List<CoinHistoryPrice> = listOf()
)


