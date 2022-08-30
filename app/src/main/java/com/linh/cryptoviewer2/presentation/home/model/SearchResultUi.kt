package com.linh.cryptoviewer2.presentation.home.model

data class SearchResultUi(
    val name: String,
    val symbol: String,
    val thumbUrl: String,
    val id: String,
    val isWatchlisted: Boolean,
    val onToggleWatch: (Boolean) -> Unit
)
