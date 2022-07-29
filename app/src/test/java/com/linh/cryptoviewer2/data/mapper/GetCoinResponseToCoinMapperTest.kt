package com.linh.cryptoviewer2.data.mapper

import com.linh.cryptoviewer2.data.remote.model.GetCoinResponseFactory
import com.linh.cryptoviewer2.domain.model.Coin
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCoinResponseToCoinMapperTest {

    @Test
    fun `Given get coin response, when map data, return coin`() {
        val response = GetCoinResponseFactory.makeGetCoinResponse()
        val mapper = GetCoinResponseToCoinMapper()

        val result = mapper.map(response)

        with(response) {
            assertEquals(id, result.id)
            assertEquals(symbol, result.symbol)
            assertEquals(name, result.name)
            assertEquals(
                Coin.Image(
                    thumbUrl = image.thumbUrl,
                    smallUrl = image.smallUrl,
                    largeUrl = image.largeUrl
                ),
                result.image
            )
            assertEquals(sentimentsVoteUpPercentage, result.sentimentsVoteUpPercentage)
            assertEquals(sentimentsVoteDownPercentage, result.sentimentsVoteDownPercentage)
            assertEquals(marketCapRank, result.marketCapRank)
            assertEquals(coinGeckoRank, result.coinGeckoRank)
            assertEquals(
                Coin.CurrentPrice(
                    usd = marketData.currentPrice.usd,
                    vnd = marketData.currentPrice.vnd,
                    btc = marketData.currentPrice.btc
                ),
                result.currentPrice
            )
            assertEquals(
                marketData.priceChangePercentage24h,
                result.priceChangePercentage24h,
                0.001
            )
        }
    }
}