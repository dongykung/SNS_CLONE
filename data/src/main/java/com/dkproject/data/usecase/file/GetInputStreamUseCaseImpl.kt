package com.dkproject.data.usecase.file

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.dkproject.domain.usecase.file.GetInputStreamUseCase
import java.io.InputStream
import javax.inject.Inject

class GetInputStreamUseCaseImpl @Inject constructor(
    private val context: Context
) :GetInputStreamUseCase{
    override fun invoke(contentUri: String): Result<InputStream> = runCatching {
        val uri = Uri.parse(contentUri)
        context.contentResolver.openInputStream(uri) ?: throw IllegalStateException("Input Stream open Fail")
    }
}