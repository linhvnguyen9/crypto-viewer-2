package com.linh.cryptoviewer2.presentation.home.model

data class CoinUi(
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val price: String,
    val priceChangePercentage24h: String
)
