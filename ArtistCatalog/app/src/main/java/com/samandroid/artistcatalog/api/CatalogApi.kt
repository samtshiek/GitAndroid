package com.samandroid.artistcatalog.api

import com.samandroid.artistcatalog.data.Catalog
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface CatalogApi {
    @GET
    fun getCatalog(@Url url: String): Call<Catalog>
}