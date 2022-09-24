package com.br.valber.testegitapi.domain.entity

data class JavaRepo(
    val name: String,
    val description: String?,
    val fullName: String,
    val avatar: String,
    val startCount: String,
    val forksCount: String,
    val pullsUrl: String,
    val login: String
)
