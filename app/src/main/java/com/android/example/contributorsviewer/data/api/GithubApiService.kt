package com.android.example.contributorsviewer.data.api

import com.android.example.contributorsviewer.data.api.dto.ContributorDto
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface GithubApiService {
    @GET("contributors")
    fun getContributors(): Deferred<List<ContributorDto>>
}

object GithubApi {
    private const val BASE_URL = "https://api.github.com/repos/android/architecture-components-samples/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }

    fun getContributors(): Deferred<List<ContributorDto>> = retrofitService.getContributors()
}