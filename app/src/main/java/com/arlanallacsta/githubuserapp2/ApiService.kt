package com.arlanallacsta.githubuserapp2

import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q")
        query: String
    ): Call<UsersResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUsersDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUsersFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUsersFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>


}