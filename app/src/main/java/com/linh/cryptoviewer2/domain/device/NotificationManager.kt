package com.linh.cryptoviewer2.domain.device

import com.linh.cryptoviewer2.domain.model.CoinMarketData

interface NotificationManager {
    fun sendPriceNotification(data: List<CoinMarketData>)
}