package com.ksusha.stockmarket.domain.repository

import com.ksusha.stockmarket.domain.model.CompanyListing
import com.ksusha.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

}