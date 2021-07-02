package com.lenatopoleva.lessonschedule.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenatopoleva.lessonschedule.R
import com.lenatopoleva.lessonschedule.mvp.presenter.SchedulePresenter
import com.lenatopoleva.lessonschedule.mvp.view.ScheduleView
import com.lenatopoleva.lessonschedule.ui.App
import com.lenatopoleva.lessonschedule.ui.BackButtonListener
import com.lenatopoleva.lessonschedule.ui.adapter.ScheduleRvAdapter
import kotlinx.android.synthetic.main.fragment_schedule.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class ScheduleFragment: MvpAppCompatFragment(), ScheduleView, BackButtonListener {

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    init {
        App.instance.appComponent.inject(this)
    }

    @InjectPresenter
    lateinit var presenter: SchedulePresenter

    val scheduleAdapter by lazy {
        ScheduleRvAdapter(presenter.scheduleListPresenter).apply {
            App.instance.appComponent.inject(
                this
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        View.inflate(context, R.layout.fragment_schedule, null)

    override fun init() {
        rv_schedule.layoutManager = LinearLayoutManager(requireContext())
        rv_schedule.adapter = scheduleAdapter
    }

    override fun updateScheduleList() {
        scheduleAdapter.notifyDataSetChanged()
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

    override fun backPressed() = presenter.backClick()

}