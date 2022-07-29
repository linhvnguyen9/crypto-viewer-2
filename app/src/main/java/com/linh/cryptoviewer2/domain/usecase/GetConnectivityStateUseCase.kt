package com.linh.cryptoviewer2.domain.usecase

import com.linh.cryptoviewer2.device.ConnectivityService
import javax.inject.Inject

class GetConnectivityStateUseCase @Inject constructor(private val connectivityService: ConnectivityService) {
    operator fun invoke() = connectivityService.connectivityState
}