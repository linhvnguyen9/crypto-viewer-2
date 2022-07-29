package com.linh.cryptoviewer2

import android.view.View
import androidx.lifecycle.ViewModel
import com.linh.cryptoviewer2.domain.model.ConnectivityState
import com.linh.cryptoviewer2.domain.usecase.GetConnectivityStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getConnectivityStateUseCase: GetConnectivityStateUseCase): ViewModel() {
    val connectivityState = getConnectivityStateUseCase()
}