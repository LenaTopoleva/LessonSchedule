package com.lenatopoleva.lessonschedule.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenatopoleva.lessonschedule.R
import com.lenatopoleva.lessonschedule.mvp.presenter.HomePresenter
import com.lenatopoleva.lessonschedule.mvp.view.HomeView
import com.lenatopoleva.lessonschedule.ui.App
import com.lenatopoleva.lessonschedule.ui.BackButtonListener
import com.lenatopoleva.lessonschedule.ui.adapter.HomeworkListRvAdapter
import com.lenatopoleva.lessonschedule.ui.adapter.LessonsRvAdapter
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
        LessonsRvAdapter(presenter.lessonsListPresenter).apply { App.instance.appComponent.inject(this)}
    }

    val homeworkListAdapter by lazy {
        HomeworkListRvAdapter(presenter.homeworkListPresenter).apply { App.instance.appComponent.inject(this)}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_home, null)

    override fun init(){
        rv_lessons.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_lessons.adapter = lessonsAdapter

        rv_homework.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_homework.adapter = homeworkListAdapter
    }

    override fun updateHomeworkList() {
        homeworkListAdapter.notifyDataSetChanged()
    }

    override fun updateLessonsList() {
        lessonsAdapter.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backClick()


}