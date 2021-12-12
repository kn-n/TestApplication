package com.example.testapplication.api

import com.example.testapplication.models.Repositories
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface API {
    @GET("search/repositories")
    fun getSearchRepositories( @Query("q") query: String):Observable<Repositories>
}