package com.android.example.contributorsviewer.data.api.dto

import com.android.example.contributorsviewer.data.model.Contributor
import com.squareup.moshi.Json

data class ContributorDto(
    val id: Long,
    @Json(name = "login") val loginName: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "url") val userDataUrl: String,
    @Json(name = "html_url") val userPageUrl: String
) {
    fun toContributor(): Contributor {
        return Contributor(
            id = id,
            userName = loginName,
            avatarUrl = avatarUrl,
            userDataUrl = userDataUrl,
            userPageUrl = userPageUrl
        )
    }
}

fun List<ContributorDto>.toContributorList(): List<Contributor> {
    return this.map { it.toContributor() }
}