package com.ksusha.stockmarket.data.repository

import com.ksusha.stockmarket.data.local.StockDatabase
import com.ksusha.stockmarket.data.mapper.toCompatyListing
import com.ksusha.stockmarket.data.remote.StockApi
import com.ksusha.stockmarket.domain.model.CompanyListing
import com.ksusha.stockmarket.domain.repository.StockRepository
import com.ksusha.stockmarket.util.Resource
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase
): StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompatyListing() }
            ))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()

            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            }
        }
    }

}