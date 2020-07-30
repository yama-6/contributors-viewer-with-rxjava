package com.android.example.contributorsviewer.data.model

data class Contributor(
    val id: Long,
    val userName: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val reposUrl: String
)