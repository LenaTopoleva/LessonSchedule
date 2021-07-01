package com.lenatopoleva.lessonschedule.mvp.view.list

interface HomeworkItemView: IItemView {
    fun setLesson(lesson: String)
    fun setDeadline(deadline: String)
    fun loadImage(image: String)
    fun setDescription(description: String)
}