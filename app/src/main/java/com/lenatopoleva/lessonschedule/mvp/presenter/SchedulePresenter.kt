package com.lenatopoleva.lessonschedule.mvp.presenter

import com.lenatopoleva.lessonschedule.mvp.model.entity.Lesson
import com.lenatopoleva.lessonschedule.mvp.model.repository.IRepository
import com.lenatopoleva.lessonschedule.mvp.presenter.list.ILessonsListPresenter
import com.lenatopoleva.lessonschedule.mvp.presenter.list.IScheduleListPresenter
import com.lenatopoleva.lessonschedule.mvp.view.ScheduleView
import com.lenatopoleva.lessonschedule.mvp.view.list.LessonItemView
import com.lenatopoleva.lessonschedule.ui.App
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SchedulePresenter: MvpPresenter<ScheduleView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var repository: IRepository
    @Inject
    lateinit var uiScheduler: Scheduler

    init {
        App.instance.appComponent.inject(this)
    }

    class ScheduleListPresenter : IScheduleListPresenter {
        override var openSkypeClickListener: (() -> Unit)? = null
        override var itemClickListener: ((LessonItemView) -> Unit)? = null

        val lessons = mutableListOf<Lesson>()

        override fun getLessonsList() = lessons.toList()

        override fun bindView(view: LessonItemView) {
            val lesson = lessons[view.pos]
            view.setTitle(lesson.title)
            view.setTime("${lesson.timeStart} - ${lesson.timeEnd}")
            lesson.image?.let { view.loadImage(lesson.image) }
            if (lesson.isOnline) view.showOpenInSkype()
            if(lesson.optionalDescription != null) view.showDescription(lesson.optionalDescription)
        }
        override fun getCount() = lessons.size
    }

    val scheduleListPresenter = ScheduleListPresenter()
    var disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadLessons()

        scheduleListPresenter.itemClickListener = { view ->
            println("Lesson: ${scheduleListPresenter.lessons[view.pos].title} clicked")
        }
        scheduleListPresenter.openSkypeClickListener = {
            viewState.openSkype()
        }

    }

    fun loadLessons() {
        disposables.add(repository.getLessons()
            .retry(3)
            .observeOn(uiScheduler)
            .subscribe(
                {
                    scheduleListPresenter.lessons.clear()
                    scheduleListPresenter.lessons.addAll(it)
                    viewState.updateScheduleList()
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