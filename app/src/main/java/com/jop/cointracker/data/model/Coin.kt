package com.jop.cointracker.data.model

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("rank")
    val rank: String = "",
    @SerializedName("symbol")
    val symbol: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("supply")
    val supply: Double? = 0.0,
    @SerializedName("maxSupply")
    val maxSupply: Double? = 0.0,
    @SerializedName("marketCapUsd")
    val marketCapUsd: Double? = 0.0,
    @SerializedName("volumeUsd24Hr")
    val volumeUsd24Hr: Double? = 0.0,
    @SerializedName("priceUsd")
    val priceUsd: Double? = 0.0,
)


