package com.dkproject.data.retrofit

import com.dkproject.data.model.dto.FileDTO
import com.dkproject.domain.model.CommonResponse
import okhttp3.MultipartBody
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileService {

    @POST("api/files")
    @Multipart
    @Headers("ContentType: multipart/form-data;")
    suspend fun uploadImage(
        @Part fileName:MultipartBody.Part,
        @Part file:MultipartBody.Part
    ):CommonResponse<FileDTO>
}