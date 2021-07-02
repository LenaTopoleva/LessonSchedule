package com.lenatopoleva.lessonschedule.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ScheduleView: MvpView {
    fun init()
    fun updateScheduleList(currentTimeLessonPosition: Int)
    fun openSkype()
}