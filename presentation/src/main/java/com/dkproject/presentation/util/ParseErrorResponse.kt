package com.dkproject.presentation.util

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import org.json.JSONObject

fun ParseErrorResponse(responseBody: String):String{
    // JSON 문자열을 UCommonResponse 객체로 파싱하는 코드
    val json = JSONObject(responseBody)
    return json.getString("errorMessage")
}

