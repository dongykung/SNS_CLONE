package com.dkproject.data.usecase.file

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.dkproject.domain.model.Image
import com.dkproject.domain.usecase.file.GetImageUseCase
import javax.inject.Inject

class GetImageUseCaseImpl @Inject constructor(
    private val context: Context
) : GetImageUseCase {
    override fun invoke(contentUri: String): Image?  {
        val uri: Uri = Uri.parse(contentUri)
        val cursor = context.contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

         return cursor?.use { c ->
            c.moveToNext()
            val nameIndex = c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeIndex = c.getColumnIndex(MediaStore.Images.Media.SIZE)
            val mimeTypeIndex = c.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)

            val name = cursor.getString(nameIndex)
            val size = cursor.getLong(sizeIndex)
            val mimeType = cursor.getString(mimeTypeIndex)
            Image(
                uri = contentUri,
                name=name,
                size=size,
                mimeType = mimeType
            )
        }


    }
}