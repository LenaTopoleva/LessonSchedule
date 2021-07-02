package com.lenatopoleva.lessonschedule.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lenatopoleva.lessonschedule.R
import com.lenatopoleva.lessonschedule.mvp.model.image.IImageLoader
import com.lenatopoleva.lessonschedule.mvp.presenter.list.IScheduleListPresenter
import com.lenatopoleva.lessonschedule.mvp.view.list.LessonItemView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_lesson.open_skype_layout
import kotlinx.android.synthetic.main.item_lesson.tv_time
import kotlinx.android.synthetic.main.item_lesson.tv_title
import kotlinx.android.synthetic.main.schedule_lesson_optional_item.*
import javax.inject.Inject

class ScheduleRvAdapter (val presenter: IScheduleListPresenter) : RecyclerView.Adapter<ScheduleRvAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        when(viewType){
            2 -> view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_lesson_optional_item, parent, false)
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_lesson_item, parent, false)
                view.layoutParams.width = parent.width
            }
        }
        return ViewHolder(view).apply {
            containerView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (presenter.getLessonsList()[position].isOptional) return 2
        return 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        presenter.bindView(holder)
    }

    override fun getItemCount() = presenter.getCount()

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LessonItemView,
        LayoutContainer {
        val container = containerView.findViewById<ImageView>(R.id.iv_image)

        override var pos = -1

        override fun setTitle(name: String) =  with(containerView){
            tv_title.text = name
        }

        override fun setTime(time: String) = with(containerView){
            tv_time.text = time
        }

        override fun loadImage(image: String) {
            imageLoader.loadInto(image, container)
        }

        override fun showOpenInSkype() {
            open_skype_layout.visibility = View.VISIBLE
        }

        override fun showDescription(optionalDescription: String) = with(containerView){
            tv_description.text = optionalDescription
        }

    }

}