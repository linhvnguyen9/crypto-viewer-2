package com.linh.cryptoviewer2.presentation.util

import android.content.Context
import androidx.annotation.StringRes

interface ResourceProvider {
    val context: Context

    fun getString(@StringRes res: Int, vararg args: Any): String

    fun getQuantityString(@StringRes id: Int, quantity: Int, vararg args: Any): String
}