package com.jop.cointracker.data.model

import com.google.gson.annotations.SerializedName

data class CoinData(
    @SerializedName("coins")
    val coins: List<Coin> = listOf()
)


