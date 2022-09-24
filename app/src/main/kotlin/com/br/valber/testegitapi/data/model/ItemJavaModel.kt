package com.br.valber.testegitapi.data.model

import com.google.gson.annotations.SerializedName

data class ItemJavaModel(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("stargazers_count")
    val stargazersCount: Int? = 0,

    @SerializedName("forks")
    val forks: Int? = 0,

    @SerializedName("owner")
    val owner: OwnerModel,

)
