package com.linh.cryptoviewer2.device.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.linh.cryptoviewer2.domain.model.CoinMarketData
import com.linh.cryptoviewer2.presentation.theme.CryptoViewer2Theme
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@SmallTest
class NotificationManagerImplTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val context = InstrumentationRegistry.getInstrumentation().targetContext

    lateinit var notificationManagerImpl: NotificationManagerImpl

    private val androidNotificationManager: NotificationManager
        get() = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

    @Before
    fun setup() {
        notificationManagerImpl = NotificationManagerImpl(context)

        val manager = androidNotificationManager
        manager.deleteNotificationChannel(NotificationManagerImpl.PRICE_NOTIFICATION_CHANNEL_ID)

        composeTestRule.setContent {
            CryptoViewer2Theme {
            }
        }
    }

    @Test
    fun givenNotificationManager_WhenCallSendNotification_ThenCreateNotificationChannel() {
        notificationManagerImpl.sendPriceNotification(
            listOf(
                CoinMarketData(
                    "asdf",
                    "asdf",
                    "asdf",
                    "asdf",
                    1,
                    12.3,
                    2.3,
                    3.4,
                    3.2
                )
            )
        )

        val manager = androidNotificationManager
        val channel = manager.getNotificationChannel(NotificationManagerImpl.PRICE_NOTIFICATION_CHANNEL_ID)

        assertEquals("Price updates", channel.name)
        assertEquals("Price updates", channel.description)
        assertEquals(NotificationManager.IMPORTANCE_LOW, channel.importance)
    }

    @Test
    fun givenNotificationManager_WhenCallSendNotification_ThenNotificationIsSent() {
        notificationManagerImpl.sendPriceNotification(
            listOf(
                CoinMarketData(
                    "asdf",
                    "asdf",
                    "asdf",
                    "asdf",
                    1,
                    12.3,
                    2.3,
                    3.4,
                    3.2
                )
            )
        )

        val manager = androidNotificationManager
        with(manager.activeNotifications.first()) {
            assertEquals("Price update", notification.extras[Notification.EXTRA_TITLE])
            assertEquals("asdf | $12.30 | 2.30%", notification.extras[Notification.EXTRA_TEXT])
        }
    }

    @Test
    fun givenNotificationManager_WhenClickOnPriceNotification_ThenOpenApp() {
        notificationManagerImpl.sendPriceNotification(
            listOf(
                CoinMarketData(
                    "asdf",
                    "asdf",
                    "asdf",
                    "asdf",
                    1,
                    12.3,
                    2.3,
                    3.4,
                    3.2
                )
            )
        )

        val manager = androidNotificationManager
        with(manager.activeNotifications.first()) {
            notification.contentIntent.send()
            composeTestRule.onNodeWithTag("scaffold").assertExists()
        }
    }

    @After
    fun tearDown() {
        val manager = androidNotificationManager
        manager.cancelAll()
    }

}