package com.lenatopoleva.lessonschedule.mvp.model.repository

import com.lenatopoleva.lessonschedule.mvp.model.entity.Homework
import com.lenatopoleva.lessonschedule.mvp.model.entity.Lesson
import io.reactivex.rxjava3.core.Single

interface IRepository {
    fun getLessons(): Single<List<Lesson>>
    fun getHomeworkList(): Single<List<Homework>>
    fun getExamsDate(): Single<String>
}