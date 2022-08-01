package com.linh.cryptoviewer2.presentation.watchlist.model

import com.linh.cryptoviewer2.domain.model.CoinMarketDataFactory
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CoinMarketDataToCoinUiMapperTest {

    private lateinit var mapper: CoinMarketDataToCoinUiMapper

    @Before
    fun setup() {
        mapper = CoinMarketDataToCoinUiMapper()
    }

    @Test
    fun `Given coin market data, when map, map success to coin UI model`() {
        val coinMarketData = CoinMarketDataFactory.makeCoinMarketData()

        val actual = mapper.map(listOf(coinMarketData))

        assertEquals(coinMarketData.name, actual[0].name)
        assertEquals(coinMarketData.symbol.uppercase(), actual[0].symbol)
        assertEquals(coinMarketData.imageUrl, actual[0].imageUrl)
        assertEquals(coinMarketData.currentPrice, actual[0].price, 0.001)
        assertEquals(coinMarketData.priceChangePercentage24h, actual[0].priceChangePercentage24h, 0.001)
    }
}