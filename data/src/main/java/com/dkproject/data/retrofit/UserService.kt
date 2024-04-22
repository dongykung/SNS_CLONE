package com.dkproject.data.retrofit

import com.dkproject.data.model.dto.UserDTO
import com.dkproject.domain.model.CommonResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST("api/users/login")
    suspend fun login(
        @Body requestBody: RequestBody
    ): CommonResponse<String>

    @POST("api/users/sign-up")
    suspend fun signUp(
        @Body requestBody: RequestBody
    ): CommonResponse<Long>

    @GET("api/users/my-page")
    suspend fun myPage():CommonResponse<UserDTO>

    @PATCH("api/users/my-page")
    suspend fun patchMyPage(
        @Body requestBody: RequestBody
    ):CommonResponse<Long>

    @GET("api/users/{id}")
    suspend fun userPage(
        @Path("id") id:Long
    ):CommonResponse<UserDTO>
}