package com.example.phonespecs.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonespecs.entity.SpecificationsModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DetailsPageVM"

class DetailsPageViewModel @Inject constructor(
    private val detailsPageRepository: DetailsPageRepository
) : ViewModel() {
    val detailsResponse = MutableLiveData<SpecificationsModel>()
    fun getPhoneDetailsBySlug(slug: String) {
        viewModelScope.launch {
            val response = detailsPageRepository.getPhoneDetailsBySlug(slug)
            detailsResponse.value = response.body()
        }
    }

}