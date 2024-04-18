package com.dkproject.data.model.Parcel

import android.os.Parcelable
import com.dkproject.domain.model.Image
import kotlinx.parcelize.Parcelize


@Parcelize
data class BoardParcel(
    val title:String,
    val content:String,
    val images:List<ImageParcel>
):Parcelable
