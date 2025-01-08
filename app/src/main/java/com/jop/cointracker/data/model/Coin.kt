package com.jop.cointracker.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coin(
    @SerializedName("uuid")
    val uuid: String = "",
    @SerializedName("symbol")
    val symbol: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("iconUrl")
    val iconUrl: String = "",
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("listedAt")
    val listedAt: Int = 0,
    @SerializedName("rank")
    val rank: Int = 0,
    @SerializedName("sparkline")
    val sparkline: List<Double?> = listOf()
): Parcelable {
    fun percentageIncrease() : Double{
        val listPrice = sparkline.mapNotNull{ it }
        val minPrice = if(sparkline.isNotEmpty()) listPrice.first() else 0.0
        val maxPrice = if(sparkline.isNotEmpty()) listPrice.last() else 0.0

        return ((maxPrice - minPrice) / minPrice) * 100
    }
}
