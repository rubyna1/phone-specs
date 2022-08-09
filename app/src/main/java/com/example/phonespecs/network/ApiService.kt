package com.example.phonespecs.network

import com.example.phonespecs.entity.PhoneModel
import com.example.phonespecs.entity.SearchModel
import com.example.phonespecs.entity.SpecificationsModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("brands/nokia-phones-1")
    suspend fun getDataByBrand(
        @Query("page") page: Int
    ): Response<PhoneModel>

    @GET("{phone_slug}")
    suspend fun getPhoneSpecifications(
        @Path("phone_slug") phone_slug: String
    ): Response<SpecificationsModel>

    @GET("search")
    suspend fun search(@Query("query") query: String): Response<SearchModel>
}