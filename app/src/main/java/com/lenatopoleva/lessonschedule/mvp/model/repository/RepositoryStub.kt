package com.lenatopoleva.lessonschedule.mvp.model.repository

import com.lenatopoleva.lessonschedule.mvp.model.entity.Homework
import com.lenatopoleva.lessonschedule.mvp.model.entity.Lesson
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryStub: IRepository {

    private val lessons = listOf<Lesson>(
        Lesson("Math", "9:30", "11:00", "https://python-scripts.com/wp-content/uploads/2020/01/math-python-libs.jpg", false, null,true),
        Lesson("Russian language", "11:00","13:00", "https://spb.hse.ru/data/2019/09/05/1538966394/Picture%203.jpg", false, null,false),
        Lesson("Geometry", "13:00","14:00", "https://1.bp.blogspot.com/-iY7Zm1dawWM/X3Kl-w41h4I/AAAAAAAAJrU/DuG9QnbWM5c845ioowxKprmzJVg_T6CoQCLcBGAsYHQ/w320-h320/math%2Bgeometry.jpg", false, null,true),
        Lesson("Literature", "14:00","15:30", "https://www.delengua.es/sites/default/files/2016-10/Spanish-Literature.jpg", true, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In et pharetra.",false ),
        Lesson("Computer Science", "15:30","17:00", "https://www.eschoolnews.com/files/2016/12/computer-science-education-768x768.jpg", false, null,false),
        Lesson("Chemistry", "17:00", "19:00", "https://previews.123rf.com/images/helenfield/helenfield1605/helenfield160500747/56423493-chemistry-pharmacology-natural-sciences-vector-doodle-set-hand-drawn-images.jpg", false, null,false)
    )

    private val homeworkList = listOf<Homework>(
        Homework("Math", "02.06 20:00", "https://python-scripts.com/wp-content/uploads/2020/01/math-python-libs.jpg", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus volutpat."),
        Homework("Chemistry", "02.06 20:00", "https://previews.123rf.com/images/helenfield/helenfield1605/helenfield160500747/56423493-chemistry-pharmacology-natural-sciences-vector-doodle-set-hand-drawn-images.jpg", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer gravida ligula quis nibh vestibulum tincidunt.")
    )

    override fun getLessons(): Single<List<Lesson>> =
        Single.just(lessons).subscribeOn(Schedulers.io())

    override fun getHomeworkList(): Single<List<Homework>> =
        Single.just(homeworkList).subscribeOn(Schedulers.io())

}
