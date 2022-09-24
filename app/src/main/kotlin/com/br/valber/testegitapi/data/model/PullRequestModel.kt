package com.br.valber.testegitapi.data.model

import com.google.gson.annotations.SerializedName

data class PullRequestModel(
    @SerializedName("title")
    val title: String?,

    @SerializedName("state")
    val state: String?,

    @SerializedName("owner")
    val owner: OwnerModel
)
