package com.lenatopoleva.lessonschedule.di.modules

import android.widget.ImageView
import com.lenatopoleva.lessonschedule.mvp.model.image.IImageLoader
import com.lenatopoleva.lessonschedule.ui.image.GlideImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageLoaderModule {

    @Singleton
    @Provides
    fun imageLoader(): IImageLoader<ImageView> = GlideImageLoader()
}