package com.android.example.contributorsviewer.api.dto

import com.squareup.moshi.Json

data class ContributorDto(
    val id: Long,
    @Json(name = "login") val userName: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "html_url") val htmlUrl: String,
    @Json(name = "repos_url") val reposUrl: String
)