package com.linh.cryptoviewer2.domain.model

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val image: Image,
    val sentimentsVoteUpPercentage: Float,
    val sentimentsVoteDownPercentage: Float,
    val marketCapRank: Int,
    val coinGeckoRank: Int,
    val currentPrice: CurrentPrice
) {
    data class Image(
        val thumbUrl: String,
        val smallUrl: String,
        val largeUrl: String
    )

    data class CurrentPrice(
        val usd: Double,
        val vnd: Double,
        val btc: Double
    )
}