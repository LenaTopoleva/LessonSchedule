package com.lenatopoleva.lessonschedule.mvp.presenter

import com.lenatopoleva.lessonschedule.mvp.model.entity.Homework
import com.lenatopoleva.lessonschedule.mvp.model.entity.Lesson
import com.lenatopoleva.lessonschedule.mvp.model.repository.IHomeScreenRepository
import com.lenatopoleva.lessonschedule.mvp.presenter.list.IHomeworkListPresenter
import com.lenatopoleva.lessonschedule.mvp.presenter.list.ILessonsListPresenter
import com.lenatopoleva.lessonschedule.mvp.view.HomeView
import com.lenatopoleva.lessonschedule.mvp.view.list.HomeworkItemView
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
        override var openSkypeClickListener: (() -> Unit)? = null
        override var itemClickListener: ((LessonItemView) -> Unit)? = null

        val lessons = mutableListOf<Lesson>()

        override fun bindView(view: LessonItemView) {
            val lesson = lessons[view.pos]
            view.setTitle(lesson.title)
            view.setTime("${lesson.timeStart} - ${lesson.timeEnd}")
            lesson.image?.let { view.loadImage(lesson.image) }
            if (lesson.isOnline) view.showOpenInSkype()
        }
        override fun getCount() = lessons.size
    }

    class HomeworkListPresenter : IHomeworkListPresenter {
        override var itemClickListener: ((HomeworkItemView) -> Unit)? = null

        val homeworkList = mutableListOf<Homework>()

        override fun bindView(view: HomeworkItemView) {
            val homework = homeworkList[view.pos]
            view.setLesson(homework.lesson)
            view.setDeadline(homework.deadline)
            homework.image?.let { view.loadImage(homework.image) }
            view.setDescription(homework.description)
        }
        override fun getCount() = homeworkList.size
    }

    val lessonsListPresenter = LessonsListPresenter()
    val homeworkListPresenter = HomeworkListPresenter()
    var disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadLessons()
        loadHomeworkList()

        lessonsListPresenter.itemClickListener = { view ->
            println("Lesson: ${lessonsListPresenter.lessons[view.pos].title} clicked")
        }
        lessonsListPresenter.openSkypeClickListener = {
            viewState.openSkype()
        }
        homeworkListPresenter.itemClickListener = { view ->
            println("Lesson: ${homeworkListPresenter.homeworkList[view.pos].lesson} clicked")
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

    fun loadHomeworkList() {
        disposables.add(homeRepository.getHomeworkList()
            .retry(3)
            .observeOn(uiScheduler)
            .subscribe(
                {
                    homeworkListPresenter.homeworkList.clear()
                    homeworkListPresenter.homeworkList.addAll(it)
                    viewState.updateHomeworkList()
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