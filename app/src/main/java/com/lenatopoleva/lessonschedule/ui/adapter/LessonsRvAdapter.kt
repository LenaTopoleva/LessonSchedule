package com.lenatopoleva.lessonschedule.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lenatopoleva.lessonschedule.R
import com.lenatopoleva.lessonschedule.mvp.model.image.IImageLoader
import com.lenatopoleva.lessonschedule.mvp.presenter.list.ILessonsListPresenter
import com.lenatopoleva.lessonschedule.mvp.view.list.LessonItemView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_lesson.*
import javax.inject.Inject

class LessonsRvAdapter (val presenter: ILessonsListPresenter) : RecyclerView.Adapter<LessonsRvAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false);
        //Устанавливаю ширину элемента = ширине экрана (если сделать это в xml, потеряется margin между элементами)
        view.layoutParams.width = (parent.width).toInt()
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

    }
}