package com.linh.cryptoviewer2

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.linh.cryptoviewer2.domain.device.WorkRequestManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class CryptoViewerApplication: Application(), Configuration.Provider  {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workRequestManager: WorkRequestManager

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initWorkManager()
    }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .setWorkManagerLogging()
        .build()

    private fun Configuration.Builder.setWorkManagerLogging(): Configuration.Builder {
        return if (BuildConfig.DEBUG) this else this.setMinimumLoggingLevel(android.util.Log.DEBUG)
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initWorkManager() {
        workRequestManager.scheduleUpdatePriceData()
    }
}