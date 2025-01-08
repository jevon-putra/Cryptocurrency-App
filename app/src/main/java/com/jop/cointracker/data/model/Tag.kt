package com.jop.cointracker.data.model


data class Tag(val title: String, val value: String)

fun getAllTags(): List<Tag>{
    return listOf(
        Tag("DeFi", "defi"),Tag("Stablecoin", "stablecoin"),
        Tag("NFT", "nft"), Tag("DEX", "dex"), Tag("Exchange", "exchange"),
        Tag("Staking", "staking"), Tag("DAO", "dao"), Tag("Meme Coin", "meme"),
        Tag("Privacy", "privacy"), Tag("Metaverse", "metaverse"), Tag("Gaming", "gaming"),
        Tag("Layer 1", "layer-1"), Tag("Layer 2", "layer-2"), Tag("Web3", "web3")
    )
}

fun getAllTimePeriods(): List<Tag>{
    return listOf(
        Tag("1H", "1h"), Tag("3H", "3h"), Tag("24H", "24h"),
        Tag("7D", "7d"), Tag("1M", "30d"), Tag("3M", "3m"),
        Tag("1Y", "1y")
    )
}