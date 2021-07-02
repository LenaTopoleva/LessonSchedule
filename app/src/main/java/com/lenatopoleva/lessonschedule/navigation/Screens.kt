package com.lenatopoleva.lessonschedule.navigation

import com.lenatopoleva.lessonschedule.ui.fragment.HomeFragment
import com.lenatopoleva.lessonschedule.ui.fragment.ScheduleFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class HomeScreen() : SupportAppScreen() {
        override fun getFragment() = HomeFragment.newInstance()
    }

    class ScheduleScreen() : SupportAppScreen() {
        override fun getFragment() = ScheduleFragment.newInstance()
    }

    class NotesScreen() : SupportAppScreen() {
    }

    class SelectedScreen() : SupportAppScreen() {
    }
}