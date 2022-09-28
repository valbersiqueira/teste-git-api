package com.br.valber.testegitapi.data.model

import com.google.gson.annotations.SerializedName

internal data class OwnerModel(
    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)
