package com.linh.cryptoviewer2.data.mapper

import com.linh.cryptoviewer2.data.remote.model.GetCoinsMarketDataResponseFactory
import org.junit.Assert.*
import org.junit.Test

class GetCoinsMarketDataResponseToCoinMarketDataMapperTest {

    @Test
    fun `Given mapper, When map data, Then return enough elements`() {
        val elements = 10
        val response = GetCoinsMarketDataResponseFactory.createGetCoinsMarketDataResponse(elements)
        val mapper = GetCoinsMarketDataResponseToCoinMarketDataMapper()

        val result = mapper.map(response)

        assertEquals(elements, result.size)
    }

    @Test
    fun `Given mapper, When map data, Then return mapped item`() {
        val response = GetCoinsMarketDataResponseFactory.createGetCoinsMarketDataResponse()
        val mapper = GetCoinsMarketDataResponseToCoinMarketDataMapper()

        val result = mapper.map(response)

        assertEquals(response[0].id, result[0].id)
        assertEquals(response[0].symbol, result[0].symbol)
        assertEquals(response[0].name, result[0].name)
        assertEquals(response[0].image, result[0].imageUrl)
        assertEquals(response[0].marketCapRank, result[0].marketCapRank)
        assertEquals(response[0].priceChangePercentage1hInCurrency, result[0].priceChangePercentage1h)
        assertEquals(response[0].priceChangePercentage24hInCurrency, result[0].priceChangePercentage24h)
        assertEquals(response[0].priceChangePercentage7dInCurrency, result[0].priceChangePercentage7d)
    }
}