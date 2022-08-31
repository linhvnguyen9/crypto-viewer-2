package com.linh.cryptoviewer2.presentation.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.core.content.ContextCompat.startActivity
import com.linh.cryptoviewer2.R

@Composable
fun CoinGeckoAttribution() {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current

        ClickableText(
            text = AnnotatedString(stringResource(R.string.all_coingeckocredit)),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW,  Uri.parse("https://coingecko.com"))
                context.startActivity(intent)
            },
            style = TextStyle(
                color = MaterialTheme.colors.onBackground
            )
        )
    }
}