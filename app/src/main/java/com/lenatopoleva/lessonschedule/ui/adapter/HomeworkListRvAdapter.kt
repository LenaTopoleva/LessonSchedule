package com.lenatopoleva.lessonschedule.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lenatopoleva.lessonschedule.R
import com.lenatopoleva.lessonschedule.mvp.model.image.IImageLoader
import com.lenatopoleva.lessonschedule.mvp.presenter.list.IHomeworkListPresenter
import com.lenatopoleva.lessonschedule.mvp.view.list.HomeworkItemView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_homework.view.*
import javax.inject.Inject

class HomeworkListRvAdapter(val presenter: IHomeworkListPresenter) : RecyclerView.Adapter<HomeworkListRvAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_homework, parent, false);
        //Устанавливаю ширину элемента = 0.7 ширины экрана
        view.layoutParams.width = (parent.width * 0.7).toInt()
        return ViewHolder(view).apply {
            containerView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        presenter.bindView(holder)
    }

    override fun getItemCount() = presenter.getCount()

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        HomeworkItemView,
        LayoutContainer {
        val container = containerView.findViewById<ImageView>(R.id.iv_image)

        override var pos = -1

        override fun setLesson(lesson: String) = with(containerView){
            tv_lesson.text = lesson
        }

        override fun setDeadline(deadline: String) = with(containerView){
            tv_deadline.text = deadline
        }

        override fun loadImage(image: String) {
            imageLoader.loadInto(image, container)
        }

        override fun setDescription(description: String) = with(containerView){
            tv_description.text = description
        }

    }
}