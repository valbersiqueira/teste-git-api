package com.br.valber.testegitapi.data.model

import com.google.gson.annotations.SerializedName

data class PullRequestModel(
    @SerializedName("title")
    val title: String?,

    @SerializedName("state")
    val state: String?,

    @SerializedName("body")
    val body: String?,

    @SerializedName("head")
    val head: HeadModel
)


data class HeadModel(
    @SerializedName("user")
    val user: OwnerModel,

    @SerializedName("repo")
    val repo: ItemJavaModel
)