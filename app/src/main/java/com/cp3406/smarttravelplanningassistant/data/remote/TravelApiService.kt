package com.cp3406.smarttravelplanningassistant.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

data class RestCountryName(
    val common: String? = null
)

data class RestCountryFlags(
    val png: String? = null
)

data class RestCountry(
    val name: RestCountryName? = null,
    val capital: List<String>? = null,
    val region: String? = null,
    val population: Long? = null,
    val flags: RestCountryFlags? = null
)

interface TravelApiService {

    @GET("v3.1/name/{country}")
    suspend fun getCountryByName(
        @Path("country") country: String
    ): List<RestCountry>
}
