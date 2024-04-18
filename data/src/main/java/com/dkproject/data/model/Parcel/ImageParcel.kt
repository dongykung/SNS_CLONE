package com.dkproject.data.model.Parcel

import android.os.Parcelable
import com.dkproject.domain.model.Image
import kotlinx.parcelize.Parcelize


@Parcelize
data class ImageParcel(
    val uri:String,
    val name:String,
    val size:Long,
    val mimeType:String
):Parcelable{
    fun toImage():Image{
        return Image(
            uri=uri,
            name=name,
            size=size,
            mimeType=mimeType
        )
    }
}



