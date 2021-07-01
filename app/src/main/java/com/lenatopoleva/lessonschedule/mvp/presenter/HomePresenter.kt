package com.lenatopoleva.lessonschedule.mvp.presenter

import com.lenatopoleva.lessonschedule.mvp.model.entity.Lesson
import com.lenatopoleva.lessonschedule.mvp.model.repository.IHomeScreenRepository
import com.lenatopoleva.lessonschedule.mvp.presenter.list.ILessonsListPresenter
import com.lenatopoleva.lessonschedule.mvp.view.HomeView
import com.lenatopoleva.lessonschedule.mvp.view.list.LessonItemView
import com.lenatopoleva.lessonschedule.ui.App
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class HomePresenter: MvpPresenter<HomeView>() {

    @Inject lateinit var router: Router
    @Inject lateinit var homeRepository: IHomeScreenRepository
    @Inject lateinit var uiScheduler: Scheduler

    init {
        App.instance.appComponent.inject(this)
    }

    class LessonsListPresenter : ILessonsListPresenter {
        override var itemClickListener: ((LessonItemView) -> Unit)? = null

        val lessons = mutableListOf<Lesson>()

        override fun bindView(view: LessonItemView) {
            val lesson = lessons[view.pos]
            view.setTitle(lesson.title)
            view.setTime("${lesson.timeStart} - ${lesson.timeEnd}")
            lesson.image?.let { view.loadImage(lesson.image) }
        }
        override fun getCount() = lessons.size
    }

    val lessonsListPresenter = LessonsListPresenter()
    var disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadLessons()

        lessonsListPresenter.itemClickListener = { view ->
            println("Lesson: ${lessonsListPresenter.lessons[view.pos].title} clicked")
        }
    }

    fun loadLessons() {
        disposables.add(homeRepository.getLessons()
            .retry(3)
            .observeOn(uiScheduler)
            .subscribe(
                {
                    lessonsListPresenter.lessons.clear()
                    lessonsListPresenter.lessons.addAll(it)
                    viewState.updateLessonsList()
                },
                { println("onError: ${it.message}") }))
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}