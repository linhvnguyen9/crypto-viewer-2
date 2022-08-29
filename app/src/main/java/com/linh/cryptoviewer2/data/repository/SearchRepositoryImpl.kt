package com.linh.cryptoviewer2.data.repository

import com.linh.cryptoviewer2.data.mapper.SearchResponseToSearchResultMapper
import com.linh.cryptoviewer2.data.remote.service.SearchService
import com.linh.cryptoviewer2.domain.model.SearchResult
import com.linh.cryptoviewer2.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
    private val searchResponseToSearchResultMapper: SearchResponseToSearchResultMapper
): SearchRepository {

    override suspend fun search(query: String): SearchResult {
        return withContext(Dispatchers.IO) {
            val response = searchService.search(query)

            return@withContext searchResponseToSearchResultMapper.map(response)
        }
    }
}