package com.linh.cryptoviewer2.device.work.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.linh.cryptoviewer2.domain.device.NotificationManager
import com.linh.cryptoviewer2.domain.usecase.GetCoinMarketDataUseCase
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class UpdatePriceDataWorkerTest {
    val context: Context = mockk(relaxed = true)
    val workerParams: WorkerParameters = mockk(relaxed = true)
    val getCoinMarketDataUseCase: GetCoinMarketDataUseCase = mockk(relaxed = true)
    val notificationManager: NotificationManager = mockk(relaxed = true)

    private lateinit var worker: UpdatePriceDataWorker

    @Before
    fun setup() {
        worker = UpdatePriceDataWorker(context, workerParams, getCoinMarketDataUseCase, notificationManager)
    }

    @Test
    fun `Given worker, When do work, Then get coin market data and send notification`() = runTest {
        worker.doWork()

        coVerify { getCoinMarketDataUseCase.invoke(any(), any()) }
        coVerify { notificationManager.sendPriceNotification(any()) }
    }

    @Test
    fun `Given worker, When do work success, Then return success result`() = runTest {
        val result = worker.doWork()

        assertTrue(result is ListenableWorker.Result.Success)
    }

    @Test
    fun `Given worker, When exception, Then return failure result`() = runTest {
        coEvery { getCoinMarketDataUseCase.invoke(any(), any()) } throws Exception()

        val result = worker.doWork()

        assertTrue(result is ListenableWorker.Result.Failure)
    }
}