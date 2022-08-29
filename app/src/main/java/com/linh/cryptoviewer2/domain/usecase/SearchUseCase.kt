package com.linh.cryptoviewer2.domain.usecase

import com.linh.cryptoviewer2.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val repository: SearchRepository) {
    suspend operator fun invoke(query: String) = repository.search(query)
}