package com.linh.cryptoviewer2.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.linh.cryptoviewer2.R
import com.linh.cryptoviewer2.presentation.home.model.SearchResultUi

@Composable
fun SearchResultItem(searchResultUi: SearchResultUi) {
    with(searchResultUi) {
        Card {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {searchResultUi.onToggleWatch(isWatchlisted.not())})  {
                    val icon = if (isWatchlisted) {
                        painterResource(R.drawable.ic_star_24)
                    } else {
                        painterResource(R.drawable.ic_star_border_24)
                    }
                    Icon(icon, null)
                }
                AsyncImage(
                    model = thumbUrl,
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
                }
            }
        }
    }
}