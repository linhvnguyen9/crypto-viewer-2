package com.linh.cryptoviewer2.domain.repository

import com.linh.cryptoviewer2.domain.model.SearchResult

interface SearchRepository {

    suspend fun search(query: String): SearchResult
}