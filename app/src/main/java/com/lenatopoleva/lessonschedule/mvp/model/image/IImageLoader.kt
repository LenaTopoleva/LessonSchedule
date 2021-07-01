package com.lenatopoleva.lessonschedule.mvp.model.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}