package com.linh.cryptoviewer2.device.notification

import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.linh.cryptoviewer2.R
import com.linh.cryptoviewer2.domain.device.NotificationManager
import com.linh.cryptoviewer2.domain.model.CoinMarketData
import com.linh.cryptoviewer2.presentation.MainActivity
import com.linh.cryptoviewer2.util.extensions.toFormattedPercentage
import com.linh.cryptoviewer2.util.extensions.toFormattedPrice
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.text.Typography.quote

class NotificationManagerImpl @Inject constructor(@ApplicationContext private val context: Context) :
    NotificationManager {

    override fun sendPriceNotification(data: List<CoinMarketData>) {
        createNotificationChannel()

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, PRICE_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Price update") // TODO: Extract string resource
            .setContentText(getPriceNotificationContent(data))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(getPriceNotificationContent(data))
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(PRICE_NOTIFICATION_ID, notification)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Price updates" // TODO: Extract string resource
            val descriptionText = "Price updates"
            val importance = android.app.NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(PRICE_NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getPriceNotificationContent(data: List<CoinMarketData>): String {
        return data.joinToString("\n") {
            "${it.symbol} | ${it.currentPrice.toFormattedPrice()} | ${it.priceChangePercentage1h.toFormattedPercentage()}"
        }
    }

    companion object {
        private const val PRICE_NOTIFICATION_CHANNEL_ID = "PRICE_NOTIFICATION_CHANNEL_ID"
        private const val PRICE_NOTIFICATION_ID = 1
    }
}