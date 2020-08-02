package com.android.example.contributorsviewer.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contributor(
    val id: Long,
    val loginName: String,
    val avatarUrl: String,
    val userDataUrl: String,
    val userPageUrl: String
) : Parcelable