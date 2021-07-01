package com.lenatopoleva.lessonschedule.mvp.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lesson (
    val title: String,
    val timeStart: String,
    val timeEnd: String,
    val image: String?
): Parcelable