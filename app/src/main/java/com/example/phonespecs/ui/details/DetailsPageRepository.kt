package com.example.phonespecs.ui.details

import com.example.phonespecs.database.AppDatabase
import com.example.phonespecs.network.ApiService
import javax.inject.Inject

class DetailsPageRepository @Inject constructor(
    val database: AppDatabase,
    private val apiService: ApiService
) {
    suspend fun getPhoneDetailsBySlug(slug: String) = apiService.getPhoneSpecifications(slug)
}