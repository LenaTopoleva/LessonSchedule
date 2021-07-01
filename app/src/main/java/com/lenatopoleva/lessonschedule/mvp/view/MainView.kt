package com.lenatopoleva.lessonschedule.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface MainView: MvpView {
    fun setHomeMenuItemChecked()
    fun setScheduleMenuItemChecked()
    fun setNotesMenuItemChecked()
    fun setSelectedMenuItemChecked()

}