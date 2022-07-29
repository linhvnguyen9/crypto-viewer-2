package com.linh.cryptoviewer2.data.mapper

import com.linh.cryptoviewer2.data.remote.model.response.GetCoinsMarketDataResponse
import com.linh.cryptoviewer2.domain.model.CoinMarketData
import com.linh.cryptoviewer2.util.Mapper
import javax.inject.Inject

class GetCoinsMarketDataResponseToCoinMarketDataMapper @Inject constructor(): Mapper<GetCoinsMarketDataResponse, List<CoinMarketData>> {
    override fun map(input: GetCoinsMarketDataResponse): List<CoinMarketData> {
        return input.map(::mapItem)
    }

    private fun mapItem(input: GetCoinsMarketDataResponse.GetCoinsMarketDataResponseItem): CoinMarketData {
        with(input) {
            return CoinMarketData(
                id = id.orEmpty(),
                symbol = symbol.orEmpty(),
                name = name.orEmpty(),
                imageUrl = image.orEmpty(),
                marketCapRank = marketCapRank ?: 0,
                currentPrice = currentPrice ?: 0.0,
                priceChangePercentage1h = priceChangePercentage1hInCurrency ?: 0.0,
                priceChangePercentage24h = priceChangePercentage24hInCurrency ?: 0.0,
                priceChangePercentage7d = priceChangePercentage7dInCurrency ?: 0.0,
            )
        }
    }
}