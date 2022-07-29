package com.linh.cryptoviewer2.device

import com.linh.cryptoviewer2.domain.model.ConnectivityState
import kotlinx.coroutines.flow.StateFlow

interface ConnectivityService {
    val connectivityState: StateFlow<ConnectivityState>
}