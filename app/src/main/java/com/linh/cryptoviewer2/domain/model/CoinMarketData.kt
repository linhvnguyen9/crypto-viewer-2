package com.linh.cryptoviewer2.domain.model

data class CoinMarketData(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val marketCapRank: Int,
    val currentPrice: Double,
    val priceChangePercentage1h: Double,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double
)
