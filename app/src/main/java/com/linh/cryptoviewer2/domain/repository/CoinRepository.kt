package com.linh.cryptoviewer2.domain.repository

import com.linh.cryptoviewer2.domain.model.Coin

interface CoinRepository {

    suspend fun getCoin(id: String): Coin
}