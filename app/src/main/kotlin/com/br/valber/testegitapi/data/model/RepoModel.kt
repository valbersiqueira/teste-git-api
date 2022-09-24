package com.br.valber.testegitapi.data.model

import com.google.gson.annotations.SerializedName

internal data class RepoModel(
    @SerializedName("name")
    val name: String,

    @SerializedName("full_name")
    val fullName: String
)
