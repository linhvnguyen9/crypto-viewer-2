package com.linh.cryptoviewer2.data.repository

import com.linh.cryptoviewer2.data.mapper.GetCoinResponseToCoinMapper
import com.linh.cryptoviewer2.data.mapper.GetCoinsMarketDataResponseToCoinMarketDataMapper
import com.linh.cryptoviewer2.data.remote.service.CoinService
import com.linh.cryptoviewer2.domain.model.Coin
import com.linh.cryptoviewer2.domain.model.CoinMarketData
import com.linh.cryptoviewer2.domain.model.FiatCurrency
import com.linh.cryptoviewer2.domain.model.PriceChangePercentage
import com.linh.cryptoviewer2.domain.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinService: CoinService,
    private val getCoinResponseToCoinMapper: GetCoinResponseToCoinMapper,
    private val getCoinsMarketDataResponseToCoinMarketDataMapper: GetCoinsMarketDataResponseToCoinMarketDataMapper,
) : CoinRepository {

    override suspend fun getCoin(id: String): Coin {
        return withContext(Dispatchers.IO) {
            val response = coinService.getCoin(id)

            getCoinResponseToCoinMapper.map(response)
        }
    }

    override suspend fun getCoinsMarketData(
        fiat: FiatCurrency,
        coinIds: List<String>,
        priceChangePercentageFilter: List<PriceChangePercentage>
    ): List<CoinMarketData> {
        return withContext(Dispatchers.IO) {
            val response = coinService.getCoinsMarketData(
                fiat.value,
                coinIds.joinToString(","),
                priceChangePercentageFilter.joinToString(",") { it.value })

            getCoinsMarketDataResponseToCoinMarketDataMapper.map(response)
        }
    }
}