package com.lenatopoleva.lessonschedule.mvp.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Homework (
    val lesson: String,
    val deadline: String,
    val image: String,
    val description: String
    ): Parcelable