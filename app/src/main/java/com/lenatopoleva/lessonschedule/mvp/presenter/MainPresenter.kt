package com.lenatopoleva.lessonschedule.mvp.presenter

import com.lenatopoleva.lessonschedule.mvp.view.MainView
import com.lenatopoleva.lessonschedule.navigation.Screens
import com.lenatopoleva.lessonschedule.ui.App
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter: MvpPresenter<MainView>() {
    @Inject
    lateinit var router: Router

    init {
        App.instance.appComponent.inject(this)
    }

    val primaryScreen = Screens.HomeScreen()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(primaryScreen)
    }

    fun backClick() {
        router.exit()
    }

    fun bottomMenuHomeClicked(): Boolean {
        print("bottomMenuGroupsClicked")
        router.navigateTo(Screens.HomeScreen())
        return true
    }

    fun bottomMenuScheduleClicked(): Boolean {
        print("bottomMenuGroupsClicked")
        router.navigateTo(Screens.ScheduleScreen())
        return true
    }
    fun bottomMenuNotesClicked(): Boolean {
        return true
    }
    fun bottomMenuSelectedClicked(): Boolean {
        return true
    }

    fun checkCurrentBottomMenuItem(currentScreenName: String) {
        println("CURRENT SCREEN: $currentScreenName")
        if (currentScreenName.contains("HomeScreen")) viewState.setHomeMenuItemChecked()
        if (currentScreenName.contains("ScheduleScreen")) viewState.setScheduleMenuItemChecked()
        if (currentScreenName.contains("NotesScreen")) viewState.setNotesMenuItemChecked()
        if (currentScreenName.contains("SelectedScreen")) viewState.setSelectedMenuItemChecked()
    }



}