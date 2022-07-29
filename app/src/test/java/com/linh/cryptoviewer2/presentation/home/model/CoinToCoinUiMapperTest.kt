package com.linh.cryptoviewer2.presentation.home.model

import com.linh.cryptoviewer2.domain.model.CoinFactory
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CoinToCoinUiMapperTest {

    private lateinit var mapper: CoinToCoinUiMapper

    @Before
    fun setup() {
        mapper = CoinToCoinUiMapper()
    }

    @Test
    fun `Given coin, when map, map success to coin UI model`() {
        val coin = CoinFactory.makeCoin()

        val actual = mapper.map(coin)

        assertEquals(coin.name, actual.name)
        assertEquals(coin.symbol.uppercase(), actual.symbol)
        assertEquals(coin.image.smallUrl, actual.imageUrl)
        assertEquals(String.format("$%.2f", coin.currentPrice.usd), actual.price)
        assertEquals(coin.priceChangePercentage24h, actual.priceChangePercentage24h, 0.001)
    }
}