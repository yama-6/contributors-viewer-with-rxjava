package com.android.example.contributorsviewer.data.api.dto

import com.android.example.contributorsviewer.data.model.Contributor
import com.android.example.contributorsviewer.data.model.ContributorDetail
import com.squareup.moshi.Json

data class ContributorDetailDto(
    val name: String,
    val followers: Int,
    val following: Int,
    @Json(name = "public_repos") val publicRepos: Int,
    @Json(name = "public_gists") val publicGists: Int
) {
    fun toContributorDetail(contributor: Contributor): ContributorDetail {
        return ContributorDetail(
            id = contributor.id,
            userName = contributor.userName,
            avatarUrl = contributor.avatarUrl,
            userSourceUrl = contributor.userDataUrl,
            userPageUrl = contributor.userPageUrl,
            name = name,
            followers = followers,
            following = following,
            publicRepos = publicRepos,
            publicGists = publicGists
        )
    }
}