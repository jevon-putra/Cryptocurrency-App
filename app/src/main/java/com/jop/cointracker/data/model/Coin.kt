package com.jop.cointracker.data.model

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id")
    val id: String,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("supply")
    val supply: String,
    @SerializedName("maxSupply")
    val maxSupply: String,
    @SerializedName("marketCapUsd")
    val marketCapUsd: String,
    @SerializedName("volumeUsd24Hr")
    val volumeUsd24Hr: String,
    @SerializedName("priceUsd")
    val priceUsd: Double,
    @SerializedName("changePercent24Hr")
    val changePercent24Hr: String,
    @SerializedName("vwap24Hr")
    val vwap24Hr: String
)


