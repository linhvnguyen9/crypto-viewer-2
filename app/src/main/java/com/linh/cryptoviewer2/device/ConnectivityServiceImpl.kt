package com.linh.cryptoviewer2.device

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.linh.cryptoviewer2.domain.model.ConnectivityState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

class ConnectivityServiceImpl @Inject constructor(private val context: Context) :
    ConnectivityService {
    private val connectivityManager by lazy { context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager }

    override val connectivityState: StateFlow<ConnectivityState>
        get() = _connectivityState
    private val _connectivityState = MutableStateFlow(ConnectivityState.CONNECTED)

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _connectivityState.update { ConnectivityState.CONNECTED }
            Timber.d("onAvailable")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            _connectivityState.update { ConnectivityState.DISCONNECTED }
            Timber.d("onLost")
        }
    }

    init {
        connectivityManager.requestNetwork(networkRequest, networkCallback)
        val currentNetwork = connectivityManager.activeNetwork

        if (currentNetwork == null) _connectivityState.update { ConnectivityState.DISCONNECTED }
    }
}