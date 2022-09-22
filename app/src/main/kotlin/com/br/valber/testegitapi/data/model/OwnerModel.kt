package com.br.valber.testegitapi.data.model

import com.google.gson.annotations.SerializedName

data class OwnerModel(
    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)
