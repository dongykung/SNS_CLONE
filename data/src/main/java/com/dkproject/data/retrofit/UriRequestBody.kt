package com.dkproject.data.retrofit

import android.util.Log
import com.dkproject.domain.usecase.file.GetInputStreamUseCase
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.FileNotFoundException
import okio.source

class UriRequestBody(
    val contentUri:String,
    val getInputStreamUseCase: GetInputStreamUseCase,
   val  contentType:MediaType?=null,
    val contentLength:Long,
):RequestBody() {
    override fun contentType(): MediaType? {
        return contentType
    }

    override fun contentLength(): Long {
        return contentLength
    }


    override fun writeTo(sink: BufferedSink) {
        try {
            getInputStreamUseCase(contentUri).onSuccess {
                it.use {inputStream->
                    sink.writeAll(inputStream.source())
                }
            }
        }catch (e:FileNotFoundException){
            Log.d("UriRequestBody", e.message.toString())
        }

    }
}