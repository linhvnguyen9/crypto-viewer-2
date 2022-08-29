package com.linh.cryptoviewer2.data.remote.model.response


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("categories")
    val categories: List<Category?>?,
    @SerializedName("coins")
    val coins: List<Coin?>?,
    @SerializedName("exchanges")
    val exchanges: List<Exchange?>?,
    @SerializedName("icos")
    val icos: List<Any?>?,
    @SerializedName("nfts")
    val nfts: List<Nft?>?
) {
    data class Category(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?
    )

    data class Coin(
        @SerializedName("api_symbol")
        val apiSymbol: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("large")
        val large: String?,
        @SerializedName("market_cap_rank")
        val marketCapRank: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("symbol")
        val symbol: String?,
        @SerializedName("thumb")
        val thumb: String?
    )

    data class Exchange(
        @SerializedName("id")
        val id: String?,
        @SerializedName("large")
        val large: String?,
        @SerializedName("market_type")
        val marketType: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("thumb")
        val thumb: String?
    )

    data class Nft(
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("symbol")
        val symbol: String?,
        @SerializedName("thumb")
        val thumb: String?
    )
}