package com.trb.netflix

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("rating") val rating: String?
)

