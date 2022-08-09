package com.example.phonespecs.entity

import com.google.gson.annotations.SerializedName

data class SearchModel(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("data") var data: SearchResponse? = SearchResponse()
)

data class SearchResponse(
    @SerializedName("title") var title: String? = null,
    @SerializedName("phones") var phones: ArrayList<Phones> = arrayListOf()
)