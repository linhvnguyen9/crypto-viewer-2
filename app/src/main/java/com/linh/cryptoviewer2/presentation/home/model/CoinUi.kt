package com.linh.cryptoviewer2.presentation.home.model

import com.linh.cryptoviewer2.R

data class CoinUi(
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val price: String,
    val priceChangePercentage24h: Double,

) {
    val priceChangePercentage24hText: String
        get() = priceChangePercentage24h.toFormattedPercentage()
    val priceChangeIconRes: Int
        get() = if (priceChangePercentage24h > 0.0) {
            R.drawable.ic_baseline_arrow_drop_up_24
        } else {
            R.drawable.ic_baseline_arrow_drop_down_24
        }
    val priceChangeDataColorRes: Int
        get() = if (priceChangePercentage24h > 0.0) {
            R.color.price_change_positive
        } else {
            R.color.price_change_negative
        }

    private fun Double.toFormattedPercentage() = String.format("%.2f", this) + "%"
}
