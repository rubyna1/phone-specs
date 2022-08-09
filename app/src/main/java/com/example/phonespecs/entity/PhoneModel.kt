package com.example.phonespecs.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PhoneModel(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("data") var data: Data? = Data()
)

data class Data(
    @SerializedName("title") var title: String? = null,
    @SerializedName("current_page") var currentPage: Int? = null,
    @SerializedName("last_page") var lastPage: Int? = null,
    @SerializedName("phones") var phones: ArrayList<Phones> = arrayListOf()
)

@Entity(tableName = "tbl_phone")
data class Phones(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("phone_name") var phoneName: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("detail") var detail: String? = null
)