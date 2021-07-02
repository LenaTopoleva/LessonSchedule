package com.lenatopoleva.lessonschedule.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
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

    var timeLineViewType: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        when(viewType){
            2 -> view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_lesson_optional_item, parent, false)
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_lesson_item, parent, false)
            }
        }
        return ViewHolder(view, timeLineViewType, presenter.currentPosition).apply {
                containerView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
                open_skype_layout.setOnClickListener { presenter.openSkypeClickListener?.invoke() }
        }
    }

    override fun getItemViewType(position: Int): Int {
        timeLineViewType = TimelineView.getTimeLineViewType(position, getItemCount());
        println("position: $position, timeline type = $timeLineViewType")
            println("position: $position")
            if (presenter.getLessonsList()[position].isOptional) return 2
            return 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        presenter.bindView(holder)
    }

    override fun getItemCount() = presenter.getCount()

    inner class ViewHolder(override val containerView: View, timeLineViewType: Int, private val currentPosition: Int) : RecyclerView.ViewHolder(containerView),
        LessonItemView,
        LayoutContainer {

        //Init timeline
        init {
            timeline.initLine(timeLineViewType)
        }

        override var pos = -1

        override fun setTitle(name: String) =  with(containerView){
            tv_title?.text = name
        }

        override fun setTime(time: String) = with(containerView){
            tv_time?.text = time
        }

        override fun loadImage(image: String) {
            val container: ImageView? = containerView.findViewById<ImageView>(R.id.iv_image)
            if (container != null) {
                imageLoader.loadInto(image, container)
            }
        }

        override fun showOpenInSkype() {
            open_skype_layout?.visibility = View.VISIBLE
        }

        override fun showDescription(optionalDescription: String) = with(containerView){
            tv_description?.text = optionalDescription
        }

        override fun updateTimeLine() {
            val previousMarkerSize = timeline.markerSize
            val newMarkerSize = previousMarkerSize * 2
            val difference = newMarkerSize - previousMarkerSize
            if (pos == currentPosition) {
                timeline.markerSize = newMarkerSize
                timeline.setPadding(timeline.paddingLeft - (difference ), 0, 0, 0)
            }
        }

    }

}