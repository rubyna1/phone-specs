package com.example.phonespecs.entity

import com.google.gson.annotations.SerializedName

data class SpecificationsModel(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("data") var data: SpecificationsData? = SpecificationsData()
)

data class SpecificationsData(
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("phone_name") var phoneName: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null,
    @SerializedName("phone_images") var phoneImages: ArrayList<String> = arrayListOf(),
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("dimension") var dimension: String? = null,
    @SerializedName("os") var os: String? = null,
    @SerializedName("storage") var storage: String? = null,
    @SerializedName("specifications") var specifications: ArrayList<Specifications> = arrayListOf()
)

data class Specifications(
    @SerializedName("title") var title: String? = null,
    @SerializedName("specs") var specs: ArrayList<Specs> = arrayListOf()
)

data class Specs(
    @SerializedName("key") var key: String? = null,
    @SerializedName("val") var value: ArrayList<String> = arrayListOf()

)