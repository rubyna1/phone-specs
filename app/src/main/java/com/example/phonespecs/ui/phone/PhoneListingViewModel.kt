package com.example.phonespecs.ui.phone

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.phonespecs.entity.Phones
import com.example.phonespecs.entity.SearchModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "PhoneDataVM"

class PhoneDataViewModel @Inject constructor(
    private val phoneDataRepository: PhoneListingRepository,
) : ViewModel() {
    var dataFromDb: LiveData<PagedList<Phones>>? = null
    var searchResponse = MutableLiveData<SearchModel>()

    fun fetchAllPhonesDataFromDb() {
        if (dataFromDb == null) {
            dataFromDb = phoneDataRepository.getAllPhonesDataFromDb()
        }
    }

    class PhonesBoundaryCallback(private val phoneDataRepository: PhoneListingRepository) :
        PagedList.BoundaryCallback<Phones>() {
        override fun onItemAtEndLoaded(itemAtEnd: Phones) {
            super.onItemAtEndLoaded(itemAtEnd)
            CoroutineScope(Dispatchers.IO).launch {
                val pageKey = phoneDataRepository.getTotalDataCount() / 40 + 1
                val pageNo = if (phoneDataRepository.getTotalDataCount() == 40) {
                    2
                } else {
                    pageKey
                }
                val response = phoneDataRepository.getPhoneDate(pageNo)
                if (response.isSuccessful) {
                    response.body()?.data?.let { phoneDataRepository.saveAllPhonesDataToDb(it.phones) }
                } else {
                    Log.i(TAG, "${response.message()}")
                }
            }
        }

        override fun onZeroItemsLoaded() {
            super.onZeroItemsLoaded()
            CoroutineScope(Dispatchers.IO).launch {
                val response = phoneDataRepository.getPhoneDate(1)
                if (response.isSuccessful) {
                    response.body()?.data?.let { phoneDataRepository.saveAllPhonesDataToDb(it.phones) }
                } else {
                    Log.i(TAG, "this is the error ${response.errorBody()}")
                }
            }
        }
    }

    fun search(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = phoneDataRepository.search(query)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    searchResponse.value = response.body()
                } else {
                    Log.i("Error", "${response.errorBody()}")
                }
            }
        }
    }
}