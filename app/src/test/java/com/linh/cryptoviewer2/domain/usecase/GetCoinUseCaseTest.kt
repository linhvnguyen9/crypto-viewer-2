package com.linh.cryptoviewer2.domain.usecase

import com.linh.cryptoviewer2.domain.repository.CoinRepository
import com.linh.cryptoviewer2.utils.TestHelper
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCoinUseCaseTest {
    private val repository: CoinRepository = mockk(relaxed = true)

    private lateinit var getCoinUseCase: GetCoinUseCase

    @Before
    fun setup() {
        getCoinUseCase = GetCoinUseCase(repository)
    }

    @Test
    fun `Given use case, When invoke, Then call get coin in repository`() = runBlocking {
        getCoinUseCase(TestHelper.randomString())

        coVerify { repository.getCoin(any()) }
    }

    @Test
    fun `Given use case, When invoke, Then get coin in repository with id`() = runBlocking {
        val id = TestHelper.randomString()

        getCoinUseCase(id)

        coVerify { repository.getCoin(id) }
    }
}