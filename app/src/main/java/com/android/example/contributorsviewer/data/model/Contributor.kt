package com.android.example.contributorsviewer.data.model

data class Contributor(
    val id: Long,
    val userName: String,
    val avatarUrl: String,
    val userPageUrl: String,
    val reposUrl: String
)