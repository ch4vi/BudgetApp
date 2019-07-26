package com.ch4vi.data.api.entity

import com.google.gson.annotations.SerializedName

data class ApiCategory(
    @SerializedName("id") val id: String?,
    @SerializedName("parent_id") val parentId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?
)