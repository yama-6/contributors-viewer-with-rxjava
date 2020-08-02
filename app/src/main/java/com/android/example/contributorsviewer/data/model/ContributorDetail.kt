package com.android.example.contributorsviewer.data.model

data class ContributorDetail(
    val id: Long,
    val loginName: String,
    val avatarUrl: String,
    val userPageUrl: String,
    val name: String?,
    val followers: Int,
    val following: Int,
    val publicRepos: Int,
    val publicGists: Int
)