package com.linh.cryptoviewer2.domain.usecase

import com.linh.cryptoviewer2.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(private val repository: CoinRepository) {
    suspend operator fun invoke(id: String) = repository.getCoin(id)
}