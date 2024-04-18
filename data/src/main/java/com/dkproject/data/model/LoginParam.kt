package com.dkproject.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable
data class LoginParam(
    val loginId:String,
    val password:String,
){
    fun toRequestBody():RequestBody{
        // Json.encodeToString(this) - 객체를 JSON 형태 문자열로 변환
        return Json.encodeToString(this).toRequestBody()
    }
}
