package com.linh.cryptoviewer2.presentation.util

import android.content.Context
import com.linh.cryptoviewer2.R

class ResourceProviderImpl(override val context: Context): ResourceProvider {

    override fun getString(res: Int, vararg args: Any): String {
        return context.getString(res, *args)
    }

    override fun getQuantityString(id: Int, quantity: Int, vararg args: Any): String {
        return context.resources.getQuantityString(id, quantity, *args)
    }
}