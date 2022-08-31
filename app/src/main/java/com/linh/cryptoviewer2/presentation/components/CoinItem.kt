package com.linh.cryptoviewer2.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.linh.cryptoviewer2.presentation.watchlist.model.CoinUi

@Composable
fun CoinItem(coinUi: CoinUi) {
    with(coinUi) {
        Card {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.width(16.dp))
                Box(Modifier.fillMaxWidth()) {
                    Column(Modifier.wrapContentHeight()) {
                        Text(name, style = MaterialTheme.typography.h6)
                        Spacer(Modifier.height(4.dp))
                        Text(symbol, style = MaterialTheme.typography.subtitle2)
                    }

                    Column(
                        Modifier
                            .wrapContentHeight()
                            .align(Alignment.CenterEnd)
                    ) {
                        Text(displayPrice, style = MaterialTheme.typography.h6, modifier = Modifier.align(Alignment.End))
                        Spacer(Modifier.height(4.dp))
                        Row(Modifier.align(Alignment.End), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = priceChangeIconRes),
                                contentDescription = null,
                                tint = colorResource(id = priceChangeDataColorRes)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                priceChangePercentage24hText,
                                style = MaterialTheme.typography.subtitle2,
                                color = colorResource(id = priceChangeDataColorRes)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CoinItemPreview() {
    CoinItem(coinUi = CoinUi("Test", "Symbol", "Url", 1.00, 1.23))
}