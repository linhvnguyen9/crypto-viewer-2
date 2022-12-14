package com.linh.cryptoviewer2.data.mapper

import com.linh.cryptoviewer2.data.remote.model.response.GetCoinResponse
import com.linh.cryptoviewer2.domain.model.Coin
import com.linh.cryptoviewer2.util.Mapper
import javax.inject.Inject

class GetCoinResponseToCoinMapper @Inject constructor(): Mapper<GetCoinResponse, Coin> {

    override fun map(input: GetCoinResponse): Coin {
        with(input) {
            return Coin(
                id = id,
                symbol = symbol,
                name = name,
                image = Coin.Image(
                    thumbUrl = image.thumbUrl,
                    smallUrl = image.smallUrl,
                    largeUrl = image.largeUrl,
                ),
                sentimentsVoteUpPercentage = sentimentsVoteUpPercentage,
                sentimentsVoteDownPercentage = sentimentsVoteDownPercentage,
                marketCapRank = marketCapRank,
                coinGeckoRank = coinGeckoRank,
                currentPrice = Coin.CurrentPrice(
                    usd = marketData.currentPrice.usd,
                    vnd = marketData.currentPrice.vnd,
                    btc = marketData.currentPrice.btc
                ),
                priceChangePercentage24h = marketData.priceChangePercentage24h
            )
        }
    }
}