package com.lenatopoleva.lessonschedule.mvp.presenter.list

import com.lenatopoleva.lessonschedule.mvp.model.entity.Lesson
import com.lenatopoleva.lessonschedule.mvp.view.list.LessonItemView

interface IScheduleListPresenter: IListPresenter<LessonItemView> {
    val currentPosition: Int
    val openSkypeClickListener: (() -> Unit)?
    fun getLessonsList(): List<Lesson>
}