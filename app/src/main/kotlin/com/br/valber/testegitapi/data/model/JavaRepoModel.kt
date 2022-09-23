package com.br.valber.testegitapi.data.model

import com.google.gson.annotations.SerializedName

data class JavaRepoModel(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("items")
    val items: List<ItemJavaModel>
)
