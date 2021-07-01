package com.lenatopoleva.lessonschedule.di

import com.lenatopoleva.lessonschedule.di.modules.AppModule
import com.lenatopoleva.lessonschedule.di.modules.HomeScreenModule
import com.lenatopoleva.lessonschedule.di.modules.ImageLoaderModule
import com.lenatopoleva.lessonschedule.di.modules.NavigationModule
import com.lenatopoleva.lessonschedule.mvp.presenter.HomePresenter
import com.lenatopoleva.lessonschedule.mvp.presenter.MainPresenter
import com.lenatopoleva.lessonschedule.ui.activity.MainActivity
import com.lenatopoleva.lessonschedule.ui.adapter.LessonsRvAdapter
import com.lenatopoleva.lessonschedule.ui.fragment.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NavigationModule::class,
    HomeScreenModule::class,
    ImageLoaderModule::class
])


interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(homePresenter: HomePresenter)
    fun inject(lessonsRvAdapter: LessonsRvAdapter)
}