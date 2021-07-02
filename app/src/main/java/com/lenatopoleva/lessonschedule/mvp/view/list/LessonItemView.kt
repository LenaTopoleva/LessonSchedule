package com.lenatopoleva.lessonschedule.mvp.view.list

interface LessonItemView: IItemView {
    fun setTitle(name: String)
    fun setTime(time: String)
    fun loadImage(image: String)
    fun showOpenInSkype()
}