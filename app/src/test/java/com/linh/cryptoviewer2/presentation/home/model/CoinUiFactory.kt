package com.linh.cryptoviewer2.presentation.home.model

import com.linh.cryptoviewer2.utils.TestHelper

object CoinUiFactory {
    fun makeCoinUi() = CoinUi(
        name = TestHelper.randomString(),
        symbol = TestHelper.randomString(),
        imageUrl = TestHelper.randomString(),
        price = TestHelper.randomString(),
        priceChangePercentage24h = TestHelper.randomDouble()
    )
}