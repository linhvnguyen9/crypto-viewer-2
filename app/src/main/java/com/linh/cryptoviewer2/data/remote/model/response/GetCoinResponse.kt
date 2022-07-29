package com.linh.cryptoviewer2.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class GetCoinResponse(
    val id: String,
    val symbol: String,
    val name: String,
    val image: Image,
    @SerializedName("sentiment_votes_up_percentage")
    val sentimentsVoteUpPercentage: Float,
    @SerializedName("sentiment_votes_down_percentage")
    val sentimentsVoteDownPercentage: Float,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("coingecko_rank")
    val coinGeckoRank: Int,
    @SerializedName("market_data")
    val marketData: MarketData,
) {
    data class Image(
        @SerializedName("thumb") val thumbUrl: String,
        @SerializedName("small") val smallUrl: String,
        @SerializedName("large") val largeUrl: String
    )

    data class MarketData(
        @SerializedName("current_price")
        val currentPrice: CurrentPrice,
        @SerializedName("price_change_percentage_24h")
        val priceChangePercentage24h: Double
    ) {
        data class CurrentPrice(
            val usd: Double,
            val vnd: Double,
            val btc: Double
        )
    }
}