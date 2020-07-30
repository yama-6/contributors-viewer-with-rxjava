package com.android.example.contributorsviewer.data.api.dto

import com.android.example.contributorsviewer.data.model.Contributor
import com.squareup.moshi.Json

data class ContributorDto(
    val id: Long,
    @Json(name = "login") val userName: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "html_url") val htmlUrl: String,
    @Json(name = "repos_url") val reposUrl: String
) {
    fun toContributor(): Contributor {
        return Contributor(
            id = id,
            userName = userName,
            avatarUrl = avatarUrl,
            htmlUrl = htmlUrl,
            reposUrl = reposUrl
        )
    }
}

fun List<ContributorDto>.toContributorList(): List<Contributor> {
    return this.map { it.toContributor() }
}