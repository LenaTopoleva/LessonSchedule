package com.lenatopoleva.lessonschedule.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenatopoleva.lessonschedule.R
import com.lenatopoleva.lessonschedule.mvp.presenter.HomePresenter
import com.lenatopoleva.lessonschedule.mvp.view.HomeView
import com.lenatopoleva.lessonschedule.ui.App
import com.lenatopoleva.lessonschedule.ui.BackButtonListener
import com.lenatopoleva.lessonschedule.ui.adapter.HomeworkListRvAdapter
import com.lenatopoleva.lessonschedule.ui.adapter.LessonsRvAdapter
import kotlinx.android.synthetic.main.countdown_layout.*
import kotlinx.android.synthetic.main.fragment_home.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class HomeFragment: MvpAppCompatFragment(), HomeView, BackButtonListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    init {
        App.instance.appComponent.inject(this)
    }

    @InjectPresenter
    lateinit var presenter: HomePresenter

    val lessonsAdapter by lazy {
        LessonsRvAdapter(presenter.lessonsListPresenter).apply { App.instance.appComponent.inject(
            this
        )}
    }

    val homeworkListAdapter by lazy {
        HomeworkListRvAdapter(presenter.homeworkListPresenter).apply { App.instance.appComponent.inject(
            this
        )}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        View.inflate(context, R.layout.fragment_home, null)

    override fun init(){
        rv_lessons.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_lessons.adapter = lessonsAdapter

        rv_homework.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_homework.adapter = homeworkListAdapter
    }

    override fun updateHomeworkList() {
        homeworkListAdapter.notifyDataSetChanged()
    }

    override fun openSkype() {
        val intent: Intent? = requireActivity().packageManager?.getLaunchIntentForPackage("com.skype.raider")
        if (intent != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Skype not found", Toast.LENGTH_SHORT).show()
            println("Skype not found")
        }
    }

    override fun updateCountDown() {
        if (presenter.days.length > 1) {
            tv_days_1.text = presenter.days[0].toString()
            tv_days_2.text = presenter.days[1].toString()
        } else tv_days_2.text = presenter.days[0].toString()
        if (presenter.hours.length > 1) {
            tv_hours_1.text = presenter.hours[0].toString()
            tv_hours_2.text = presenter.hours[1].toString()
        } else tv_hours_2.text = presenter.hours[0].toString()
        if (presenter.minutes.length > 1) {
            tv_minutes_1.text = presenter.minutes[0].toString()
            tv_minutes_2.text = presenter.minutes[1].toString()
        } else  tv_minutes_2.text = presenter.minutes[0].toString()
    }

    override fun updateLessonsList(currentPosition: Int) {
        rv_lessons?.layoutManager?.scrollToPosition(currentPosition)
        lessonsAdapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun backPressed() = presenter.backClick()


}