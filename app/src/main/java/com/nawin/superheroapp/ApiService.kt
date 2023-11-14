package com.nawin.superheroapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    //Peticiones GET de nuestra appi:

    @GET("api/10228338570923467/search/{name}")

    suspend fun  getSuperheroes(@Path("name") superheroName:String):Response<SuperHeroDataResponse>

    @GET("api/10228338570923467/{id}")

    suspend fun  getSuperHeroDetail(@Path("id") superheroId:String):Response<SuperHeroDetailResponse>
}