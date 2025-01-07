package com.jop.cointracker.data.model

import com.google.gson.annotations.SerializedName

data class CoinDetailData(
    @SerializedName("coin")
    val coin: CoinDetail = CoinDetail()
)


