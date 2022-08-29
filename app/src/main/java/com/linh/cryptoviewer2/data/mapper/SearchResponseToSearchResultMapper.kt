package com.linh.cryptoviewer2.data.mapper

import com.linh.cryptoviewer2.data.remote.model.response.GetCoinsMarketDataResponse
import com.linh.cryptoviewer2.data.remote.model.response.SearchResponse
import com.linh.cryptoviewer2.domain.model.CoinMarketData
import com.linh.cryptoviewer2.domain.model.SearchResult
import com.linh.cryptoviewer2.util.Mapper
import com.linh.cryptoviewer2.util.extensions.orZero
import javax.inject.Inject

class SearchResponseToSearchResultMapper @Inject constructor(): Mapper<SearchResponse, SearchResult> {
    override fun map(input: SearchResponse): SearchResult {
        return SearchResult(
            input.coins?.map(::mapCoinItem) ?: emptyList()
        )
    }

    private fun mapCoinItem(coin: SearchResponse.Coin): SearchResult.Coin {
        return with(coin) {
            SearchResult.Coin(id.orEmpty(), name.orEmpty(), symbol.orEmpty(), apiSymbol.orEmpty(), marketCapRank.orZero(), thumb.orEmpty(), large.orEmpty())
        }
    }
}