package com.lenatopoleva.lessonschedule.mvp.presenter.list

import com.lenatopoleva.lessonschedule.mvp.view.list.IItemView

interface IListPresenter<V : IItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}