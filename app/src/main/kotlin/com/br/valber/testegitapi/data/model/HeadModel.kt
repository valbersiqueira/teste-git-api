package com.br.valber.testegitapi.data.model

import com.google.gson.annotations.SerializedName

internal data class HeadModel(
    @SerializedName("user")
    val user: OwnerModel,

    @SerializedName("repo")
    val repo: ItemJavaModel?
)