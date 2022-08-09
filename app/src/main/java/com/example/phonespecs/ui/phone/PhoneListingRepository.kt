package com.example.phonespecs.ui.phone

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.phonespecs.database.AppDatabase
import com.example.phonespecs.entity.Phones
import com.example.phonespecs.network.ApiService
import javax.inject.Inject

class PhoneListingRepository @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase
) {
    suspend fun getPhoneDate(page: Int) = apiService.getDataByBrand(page)

    fun saveAllPhonesDataToDb(models: List<Phones>) =
        database.phoneDataDao().saveAllPhonesData(models)

    fun getTotalDataCount() = database.phoneDataDao().getTotalDataCount()

    fun getAllPhonesDataFromDb(): LiveData<PagedList<Phones>> {
        val factory = database.phoneDataDao().getAllPhonesData()
        val builder = LivePagedListBuilder(
            factory,
            PagedList.Config.Builder().setEnablePlaceholders(true).setPageSize(40).build()
        )
        return builder.setBoundaryCallback(PhoneDataViewModel.PhonesBoundaryCallback(this)).build()
    }

    suspend fun search(query: String) = apiService.search(query)
}