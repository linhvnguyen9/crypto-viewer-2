package com.linh.cryptoviewer2.presentation.home.model

import com.linh.cryptoviewer2.data.mapper.Mapper
import com.linh.cryptoviewer2.domain.model.Coin
import javax.inject.Inject

class CoinToCoinUiMapper @Inject constructor(): Mapper<Coin, CoinUi> {
    override fun map(input: Coin): CoinUi {
        with(input) {
            return CoinUi(
                name = name,
                symbol = symbol,
                imageUrl = image.smallUrl
            )
        }
    }
}