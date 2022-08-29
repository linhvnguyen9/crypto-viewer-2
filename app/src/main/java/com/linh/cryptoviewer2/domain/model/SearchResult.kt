package com.linh.cryptoviewer2.domain.model

data class SearchResult(
    val coins: List<Coin>
) {
    data class Coin(
        val id: String,
        val name: String,
        val symbol: String,
        val apiSymbol: String,
        val marketCapRank: Int,
        val thumbUrl: String,
        val largeImageUrl: String
    )
}