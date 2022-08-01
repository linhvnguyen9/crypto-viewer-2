package com.linh.cryptoviewer2.data.remote.model.response


import com.google.gson.annotations.SerializedName

class GetCoinsMarketDataResponse() : ArrayList<GetCoinsMarketDataResponse.GetCoinsMarketDataResponseItem>() {
    constructor(items: List<GetCoinsMarketDataResponseItem>) : this() {
        this.addAll(items)
    }

    data class GetCoinsMarketDataResponseItem(
        @SerializedName("ath")
        val ath: Double?,
        @SerializedName("ath_change_percentage")
        val athChangePercentage: Double?,
        @SerializedName("ath_date")
        val athDate: String?,
        @SerializedName("atl")
        val atl: Double?,
        @SerializedName("atl_change_percentage")
        val atlChangePercentage: Double?,
        @SerializedName("atl_date")
        val atlDate: String?,
        @SerializedName("circulating_supply")
        val circulatingSupply: Double?,
        @SerializedName("current_price")
        val currentPrice: Double?,
        @SerializedName("fully_diluted_valuation")
        val fullyDilutedValuation: Long?,
        @SerializedName("high_24h")
        val high24h: Double?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("last_updated")
        val lastUpdated: String?,
        @SerializedName("low_24h")
        val low24h: Double?,
        @SerializedName("market_cap")
        val marketCap: Long?,
        @SerializedName("market_cap_change_24h")
        val marketCapChange24h: Double?,
        @SerializedName("market_cap_change_percentage_24h")
        val marketCapChangePercentage24h: Double?,
        @SerializedName("market_cap_rank")
        val marketCapRank: Int?,
        @SerializedName("max_supply")
        val maxSupply: Double?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("price_change_24h")
        val priceChange24h: Double?,
        @SerializedName("price_change_percentage_1h_in_currency")
        val priceChangePercentage1hInCurrency: Double?,
        @SerializedName("price_change_percentage_24h")
        val priceChangePercentage24h: Double?,
        @SerializedName("price_change_percentage_24h_in_currency")
        val priceChangePercentage24hInCurrency: Double?,
        @SerializedName("price_change_percentage_7d_in_currency")
        val priceChangePercentage7dInCurrency: Double?,
        @SerializedName("roi")
        val roi: Roi?,
        @SerializedName("symbol")
        val symbol: String?,
        @SerializedName("total_supply")
        val totalSupply: Double?,
        @SerializedName("total_volume")
        val totalVolume: Long?
    ) {
        data class Roi(
            @SerializedName("currency")
            val currency: String?,
            @SerializedName("percentage")
            val percentage: Double?,
            @SerializedName("times")
            val times: Double?
        )
    }
}