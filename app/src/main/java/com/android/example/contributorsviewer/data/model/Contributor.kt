package com.android.example.contributorsviewer.data.model

import com.squareup.moshi.Json

data class Contributor(
    val id: Long,
    val userName: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val reposUrl: String
)