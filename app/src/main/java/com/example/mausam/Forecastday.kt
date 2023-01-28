package com.example.mausam


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Forecastday(
    @SerializedName("astro")
    val astro: @RawValue Astro,
    @SerializedName("date")
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    @SerializedName("day")
    val day:@RawValue Day,
    @SerializedName("hour")
    val hour: @RawValue List<Hour>
):Parcelable