package com.linh.cryptoviewer2.device.work

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.linh.cryptoviewer2.device.work.worker.UpdatePriceDataWorker
import com.linh.cryptoviewer2.domain.device.WorkRequestManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkRequestManagerImpl @Inject constructor(@ApplicationContext private val context: Context) :
    WorkRequestManager {

    override fun scheduleUpdatePriceData() {
        val updatePriceDataWorkRequest =
            PeriodicWorkRequestBuilder<UpdatePriceDataWorker>(1, TimeUnit.HOURS).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(UPDATE_PRICE_DATA_WORK, ExistingPeriodicWorkPolicy.REPLACE, updatePriceDataWorkRequest)
    }

    companion object {
        const val UPDATE_PRICE_DATA_WORK = "UPDATE_PRICE_DATA_WORK"
    }
}