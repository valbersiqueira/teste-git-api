package com.br.valber.testegitapi.data.model

import com.google.gson.annotations.SerializedName

internal data class PullRequestModel(
    @SerializedName("title")
    val title: String?,

    @SerializedName("state")
    val state: String?,

    @SerializedName("body")
    val body: String?,

    @SerializedName("head")
    val head: HeadModel
)
