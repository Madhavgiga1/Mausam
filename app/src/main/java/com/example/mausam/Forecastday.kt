package com.example.mausam

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Forecastday(
    val astro: @RawValue Astro,
    val date: String,
    val date_epoch: Int,
    val day: @RawValue Day,
    val hour: @RawValue List<Hour>
): Parcelable