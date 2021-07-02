package com.lenatopoleva.lessonschedule.mvp.presenter

import android.os.CountDownTimer
import com.lenatopoleva.lessonschedule.mvp.model.entity.Homework
import com.lenatopoleva.lessonschedule.mvp.model.entity.Lesson
import com.lenatopoleva.lessonschedule.mvp.model.repository.IRepository
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
import java.util.*
import javax.inject.Inject

class HomePresenter: MvpPresenter<HomeView>() {

    @Inject lateinit var router: Router
    @Inject lateinit var repository: IRepository
    @Inject lateinit var uiScheduler: Scheduler

    private var examDate: String = ""
    private var countDownTime: Long = 0
    var days: String = ""
    var hours: String = ""
    var minutes: String = ""
    private var countDownTimer: CountDownTimer? = null


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
            homework.image.let { view.loadImage(homework.image) }
            view.setDescription(homework.description)
        }
        override fun getCount() = homeworkList.size
    }

    val lessonsListPresenter = LessonsListPresenter()
    val homeworkListPresenter = HomeworkListPresenter()
    private var disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadLessons()
        loadHomeworkList()
        getExamsDate()

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

    private fun loadLessons() {
        disposables.add(repository.getLessons()
            .retry(3)
            .observeOn(uiScheduler)
            .subscribe(
                {
                    lessonsListPresenter.lessons.clear()
                    lessonsListPresenter.lessons.addAll(it)
                    viewState.updateLessonsList(findCurrentTimeLessonPosition(it))
                },
                { println("onError: ${it.message}") }))
    }

    private fun loadHomeworkList() {
        disposables.add(repository.getHomeworkList()
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

    private fun getExamsDate(){
        disposables.add(repository.getExamsDate()
            .observeOn(uiScheduler)
            .subscribe(
                {
                    examDate = it
                    getCountDownTime()
                    initTimer()
                },
                {println("onError: ${it.message}") }
            ))
    }

    private fun findCurrentTimeLessonPosition(list: List<Lesson>): Int {
        val currentTime = Calendar.getInstance().time.hours
        println("Time NOW: $currentTime")
        val pos: Int = 0
        for(i in list.indices) {
            var previousLessonEndTimeHour: Int = 0
            val lessonStartTimeHour = list[i].timeStart.subSequence(0, 2).toString().toInt()
            if (i > 0) previousLessonEndTimeHour = list[i-1].timeEnd.subSequence(0, 2).toString().toInt()
            if (lessonStartTimeHour == currentTime) {
                return i
            } else if (lessonStartTimeHour > currentTime && i > 0 && previousLessonEndTimeHour < currentTime) return i
            else if (lessonStartTimeHour > currentTime && i > 0 && previousLessonEndTimeHour >= currentTime) return i - 1
        }
        return pos
    }

    private fun getCountDownTime(){
        val examsTime = Date.parse(examDate)
        println("examsTime: $examsTime")
        val currentTime = Calendar.getInstance().timeInMillis
        println("currentTime: $currentTime")
        countDownTime = examsTime - currentTime
        println("countDownTime: $countDownTime")
    }

    private fun initTimer(){
        countDownTimer = object : CountDownTimer(countDownTime, 10000) {
            override fun onTick(millisUntilFinished: Long) {
                println("Tick!")
                val countDownTimeMinutes = millisUntilFinished / 60000 //Получаем время в минутах
                val daysLong = (countDownTimeMinutes / 1440)
                days = daysLong.toString()
                val hoursLong = (countDownTimeMinutes - (daysLong * 1440))/60
                hours = hoursLong.toString()
                val minutesLong = ((countDownTimeMinutes - (daysLong * 1440) -(hoursLong * 60)))
                minutes = minutesLong.toString()
                print("days: $days, hours: $hours, minutes: $minutes ")
                viewState.updateCountDown()
            }
            override fun onFinish() {}
        }
        countDownTimer?.start()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        countDownTimer?.cancel()
    }

    fun onStop() {
        countDownTimer?.cancel()
    }
}