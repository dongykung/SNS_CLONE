package com.dkproject.data.retrofit

import com.dkproject.data.model.dto.BoardDTO
import com.dkproject.domain.model.CommonResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardService {

    @POST("api/boards")
    suspend fun postBoard(
        @Body requestBody:RequestBody
    ):CommonResponse<Long>

    @GET("api/boards")
    suspend fun getBoard(
        @Query("page") page:Int,
        @Query("size") size:Int,
    ):CommonResponse<List<BoardDTO>>

    @DELETE("api/boards/{id}")
    suspend fun deleteBoard(
        @Path("id")id:Long
    ):CommonResponse<Long>

    @POST("api/boards/{id}/comments")
    suspend fun postComment(
        @Path("id")id:Long,
        @Body requestBody: RequestBody
    ):CommonResponse<Long>

    @DELETE("api/boards/{boardId}/comments/{commentId}")
    suspend fun deleteComment(
        @Path("boardId") boardId:Long,
        @Path("commentId")commentId:Long
    ):CommonResponse<Long>
}