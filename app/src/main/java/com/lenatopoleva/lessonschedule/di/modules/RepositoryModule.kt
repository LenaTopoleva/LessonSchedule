package com.lenatopoleva.lessonschedule.di.modules


import com.lenatopoleva.lessonschedule.mvp.model.repository.IRepository
import com.lenatopoleva.lessonschedule.mvp.model.repository.RepositoryStub
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun repository(): IRepository = RepositoryStub()

}