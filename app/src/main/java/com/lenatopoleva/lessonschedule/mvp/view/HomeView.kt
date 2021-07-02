package com.lenatopoleva.lessonschedule.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface HomeView: MvpView {
    fun updateLessonsList(currentPosition: Int)
    fun init()
    fun updateHomeworkList()
    fun openSkype()
    fun updateCountDown()

}