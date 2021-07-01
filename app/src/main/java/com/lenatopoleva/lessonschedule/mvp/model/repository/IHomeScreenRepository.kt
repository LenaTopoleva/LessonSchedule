package com.lenatopoleva.lessonschedule.mvp.model.repository

import com.lenatopoleva.lessonschedule.mvp.model.entity.Lesson
import io.reactivex.rxjava3.core.Single

interface IHomeScreenRepository {
    fun getLessons(): Single<List<Lesson>>
}