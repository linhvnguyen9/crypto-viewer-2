package com.linh.cryptoviewer2.presentation.home.model

import com.linh.cryptoviewer2.utils.TestHelper
import org.junit.Assert.*
import org.junit.Test

class CoinUiTest {

    @Test
    fun `Given coin ui and price change percentage 24h, when get price change percentage 24h, return formatted string`() {
        val coinUi = CoinUi(
            TestHelper.randomString(),
            TestHelper.randomString(),
            TestHelper.randomString(),
            TestHelper.randomDouble(),
            23.0
        )

        val actual = coinUi.priceChangePercentage24hText

        assertEquals(String.format("%.2f", coinUi.priceChangePercentage24h) + "%", actual)
    }

    // TODO: Check why we cannot ref drawable resources in test

//    @Test
//    fun `Given coin ui and positive price change percentage 24h, when get price change icon, return correct drawable resource`() {
//        val coinUi = CoinUi(
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            23.0
//        )
//
//        val actual = coinUi.priceChangeIconRes
//
//        assertEquals(R.drawable.ic_baseline_arrow_drop_up_24, actual)
//    }
//
//    @Test
//    fun `Given coin ui and negative price change percentage 24h, when get price change icon, return correct drawable resource`() {
//        val coinUi = CoinUi(
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            -23.0
//        )
//
//        val actual = coinUi.priceChangeIconRes
//
//        assertEquals(R.drawable.ic_baseline_arrow_drop_down_24, actual)
//    }
//
//    @Test
//    fun `Given coin ui and positive price change percentage 24h, when get price change data color, return correct color resource`() {
//        val coinUi = CoinUi(
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            23.0
//        )
//
//        val actual = coinUi.priceChangeDataColorRes
//
//        assertEquals(R.drawable.price_change_positive, actual)
//    }
//
//    @Test
//    fun `Given coin ui and negative price change percentage 24h, when get price change data color, return correct color resource`() {
//        val coinUi = CoinUi(
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            TestHelper.randomString(),
//            -23.0
//        )
//
//        val actual = coinUi.priceChangeDataColorRes
//
//        assertEquals(R.color.price_change_negative, actual)
//    }

    @Test
    fun `Given coin ui, when get display price, return correct string`() {
        val coinUi = CoinUiFactory.makeCoinUi()

        val actual = coinUi.displayPrice

        assertEquals(actual, String.format("$%.2f", coinUi.price))
    }
}