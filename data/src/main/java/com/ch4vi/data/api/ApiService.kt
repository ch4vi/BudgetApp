package com.ch4vi.data.api

import com.ch4vi.data.api.entity.ApiCategory
import com.ch4vi.data.api.entity.ApiLocation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("location/list")
    fun getLocationList(): Call<List<ApiLocation>>

    @GET("category/list")
    fun getCategoryList(): Call<List<ApiCategory>>

    @GET("category/list/{id}")
    fun getCategoryListByParentId(@Path("id") parentId: String): Call<List<ApiCategory>>
}

