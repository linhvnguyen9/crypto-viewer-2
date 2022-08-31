package com.linh.cryptoviewer2.device.work.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.linh.cryptoviewer2.domain.device.NotificationManager
import com.linh.cryptoviewer2.domain.model.FiatCurrency
import com.linh.cryptoviewer2.domain.model.PriceChangePercentage
import com.linh.cryptoviewer2.domain.usecase.GetCoinMarketDataUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UpdatePriceDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getCoinMarketDataUseCase: GetCoinMarketDataUseCase,
    private val notificationManager: NotificationManager
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        try {
            val result = getCoinMarketDataUseCase(fiatCurrency = FiatCurrency.USD, filter = listOf(PriceChangePercentage.CHANGE_24_HOURS))
            notificationManager.sendPriceNotification(result)
        } catch (e: Exception) {
            return Result.failure()
        }

        return Result.success()
    }
}