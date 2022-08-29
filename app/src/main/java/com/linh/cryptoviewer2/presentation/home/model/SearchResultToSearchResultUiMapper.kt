package com.linh.cryptoviewer2.presentation.home.model

import com.linh.cryptoviewer2.domain.model.SearchResult
import com.linh.cryptoviewer2.util.Mapper
import javax.inject.Inject

class SearchResultToSearchResultUiMapper @Inject constructor(): Mapper<SearchResult, List<SearchResultUi>> {

    override fun map(input: SearchResult): List<SearchResultUi> {
        return input.coins.map {
            with(it) {
                SearchResultUi(
                    name = name,
                    symbol = symbol,
                    thumbUrl = largeImageUrl,
                    id = id
                )
            }
        }
    }
}