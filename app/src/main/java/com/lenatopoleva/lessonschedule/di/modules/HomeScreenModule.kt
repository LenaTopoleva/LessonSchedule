package com.lenatopoleva.lessonschedule.di.modules

import com.lenatopoleva.lessonschedule.mvp.model.repository.HomeScreenRepositoryStub
import com.lenatopoleva.lessonschedule.mvp.model.repository.IHomeScreenRepository
import dagger.Module
import dagger.Provides

@Module
class HomeScreenModule {

    @Provides
    fun homeRepository(): IHomeScreenRepository = HomeScreenRepositoryStub()

}