package com.jop.cointracker.data.model

import com.google.gson.annotations.SerializedName

data class CoinDetail(
    @SerializedName("uuid")
    val uuid: String = "",
    @SerializedName("symbol")
    val symbol: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("iconUrl")
    val iconUrl: String = "",
    @SerializedName("websiteUrl")
    val websiteUrl: String = "",
    @SerializedName("links")
    val links: List<Link> = listOf(),
    @SerializedName("supply")
    val supply: Supply = Supply(),
    @SerializedName("numberOfMarkets")
    val numberOfMarkets: Int = 0,
    @SerializedName("numberOfExchanges")
    val numberOfExchanges: Int = 0,
    @SerializedName("24hVolume")
    val hVolume: Double = 0.0,
    @SerializedName("marketCap")
    val marketCap: Double = 0.0,
    @SerializedName("fullyDilutedMarketCap")
    val fullyDilutedMarketCap: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("btcPrice")
    val btcPrice: String = "",
    @SerializedName("priceAt")
    val priceAt: Int = 0,
    @SerializedName("change")
    val change: String = "",
    @SerializedName("rank")
    val rank: Int = 0,
    @SerializedName("allTimeHigh")
    val allTimeHigh: AllTimeHigh = AllTimeHigh(),
    @SerializedName("tier")
    val tier: Int = 0,
    @SerializedName("lowVolume")
    val lowVolume: Boolean = false,
    @SerializedName("listedAt")
    val listedAt: Int = 0,
    @SerializedName("tags")
    val tags: List<String> = listOf()
) {
    data class Link(
        @SerializedName("name")
        val name: String = "",
        @SerializedName("url")
        val url: String = "",
        @SerializedName("type")
        val type: String = ""
    )

    data class Supply(
        @SerializedName("confirmed")
        val confirmed: Boolean = false,
        @SerializedName("supplyAt")
        val supplyAt: Int = 0,
        @SerializedName("max")
        val max: Double = 0.0,
        @SerializedName("total")
        val total: Double = 0.0,
        @SerializedName("circulating")
        val circulating: Double = 0.0
    )

    data class AllTimeHigh(
        @SerializedName("price")
        val price: Double = 0.0,
        @SerializedName("timestamp")
        val timestamp: Int = 0
    )
}