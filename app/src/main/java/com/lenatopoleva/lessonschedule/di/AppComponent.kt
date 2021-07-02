package com.lenatopoleva.lessonschedule.di

import com.lenatopoleva.lessonschedule.di.modules.*
import com.lenatopoleva.lessonschedule.mvp.presenter.HomePresenter
import com.lenatopoleva.lessonschedule.mvp.presenter.MainPresenter
import com.lenatopoleva.lessonschedule.mvp.presenter.SchedulePresenter
import com.lenatopoleva.lessonschedule.ui.activity.MainActivity
import com.lenatopoleva.lessonschedule.ui.adapter.HomeworkListRvAdapter
import com.lenatopoleva.lessonschedule.ui.adapter.LessonsRvAdapter
import com.lenatopoleva.lessonschedule.ui.adapter.ScheduleRvAdapter
import com.lenatopoleva.lessonschedule.ui.fragment.HomeFragment
import com.lenatopoleva.lessonschedule.ui.fragment.ScheduleFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NavigationModule::class,
    RepositoryModule::class,
    ImageLoaderModule::class
])


interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(homePresenter: HomePresenter)
    fun inject(lessonsRvAdapter: LessonsRvAdapter)
    fun inject(homeworkListRvAdapter: HomeworkListRvAdapter)
    fun inject(schedulePresenter: SchedulePresenter)
    fun inject(scheduleFragment: ScheduleFragment)
    fun inject(scheduleRvAdapter: ScheduleRvAdapter)
}