package com.lenatopoleva.lessonschedule.ui.activity

import android.os.Bundle
import com.lenatopoleva.lessonschedule.R
import com.lenatopoleva.lessonschedule.mvp.presenter.MainPresenter
import com.lenatopoleva.lessonschedule.mvp.view.MainView
import com.lenatopoleva.lessonschedule.ui.App
import com.lenatopoleva.lessonschedule.ui.BackButtonListener
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class MainActivity  : MvpAppCompatActivity(), MainView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @InjectPresenter
    lateinit var presenter: MainPresenter

    val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {
        override fun applyCommands(commands: Array<out Command>) {
            presenter.checkCurrentBottomMenuItem(getCurrentScreenName(commands))
            super.applyCommands(commands)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnItemSelectedListener { item ->
            when (item) {
                R.id.home -> presenter.bottomMenuHomeClicked()
                R.id.schedule -> presenter.bottomMenuScheduleClicked()
                R.id.notes -> presenter.bottomMenuNotesClicked()
                R.id.selected -> presenter.bottomMenuSelectedClicked()
            }
        }
    }
    fun getCurrentScreenName(commands: Array<out Command>): String {
        var currentScreenName = ""
        when (val lastCommand: Command = commands[commands.size - 1]) {
            is Replace -> currentScreenName = lastCommand.screen.screenKey
            is Forward -> currentScreenName = lastCommand.screen.screenKey
            is Back -> currentScreenName = getPreviousFragmentName() ?: ""
        }
        return currentScreenName
    }

    private fun getPreviousFragmentName(): String? {
        return when (supportFragmentManager.backStackEntryCount) {
            0 -> null
            1 -> presenter.primaryScreen.toString()
            else -> supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 2).name
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClick()
    }

    override fun setHomeMenuItemChecked() {
        bottom_navigation.setItemSelected(R.id.home)
    }

    override fun setScheduleMenuItemChecked() {
        bottom_navigation.setItemSelected(R.id.schedule)
    }

    override fun setNotesMenuItemChecked() {
        bottom_navigation.setItemSelected(R.id.notes)
    }

    override fun setSelectedMenuItemChecked() {
        bottom_navigation.setItemSelected(R.id.selected)
    }

}

