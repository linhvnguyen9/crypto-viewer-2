package com.linh.cryptoviewer2.util.extensions

fun Double.toFormattedPercentage() = String.format("%.2f", this) + "%"

fun Double.toFormattedPrice() = String.format("$%.2f", this)