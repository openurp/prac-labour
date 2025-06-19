/*
 * Copyright (C) 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openurp.prac.labour.web.action

import org.beangle.data.dao.OqlBuilder
import org.beangle.ems.app.web.WebBusinessLogger
import org.beangle.security.Securities
import org.beangle.webmvc.view.View
import org.openurp.base.std.model.Student
import org.openurp.prac.labour.model.{LabourCourseSyllabus, LabourLesson, LabourLessonTaker}
import org.openurp.starter.web.support.StudentSupport

import java.time.{Instant, LocalDate}

class StdAction extends StudentSupport {
  var businessLogger: WebBusinessLogger = _

  override protected def projectIndex(std: Student): View = {
    val project = std.project
    val takers = entityDao.findBy(classOf[LabourLessonTaker], "std", std)
    put("takers", takers)
    put("takerLessons", takers.map(x => x.lesson -> x).toMap)

    val query = OqlBuilder.from(classOf[LabourLesson], "l")
    query.where("l.project=:project", project)
    query.where("l.openOn >= :today", LocalDate.now)
    query.where(":now between l.chooseBeginAt and l.chooseEndAt", Instant.now)
    query.cacheable()
    val lessons = entityDao.search(query).filter(x => x.grades.isEmpty || x.grades.contains(std.grade))
    put("lessons", lessons.groupBy(_.course.courseType))
    forward()
  }

  def choose(): View = {
    val lesson = entityDao.get(classOf[LabourLesson], getLongId("lesson"))
    val std = getStudent
    if (lesson.takers.map(_.std).toSet.contains(std)) {
      redirect("index", "你已经选择过该课程")
    } else {
      val sql = s"update ${classOf[LabourLesson].getName} l set stdCount = stdCount+1 where l.stdCount < l.capacity and l.id=?1"
      val updatedCount = entityDao.executeUpdate(sql, lesson.id)
      if (updatedCount > 0) {
        val taker = new LabourLessonTaker(lesson, std)
        entityDao.saveOrUpdate(taker)
        businessLogger.info(s"选择了${lesson.course.name}(${lesson.crn}),时间为${lesson.openOn.get} ${lesson.beginAt}", taker.id, Map("lessonId" -> lesson.id))
        redirect("index", "选课成功")
      } else {
        redirect("index", "人数已满，请选择其他课程。")
      }
    }
  }

  def drop(): View = {
    val taker = entityDao.get(classOf[LabourLessonTaker], getLongId("taker"))
    if (taker.std.user.code == Securities.user) {
      val sql = s"update ${classOf[LabourLesson].getName} l set stdCount=stdCount-1 where l.id=?1 and stdCount>0"
      entityDao.executeUpdate(sql, taker.lesson.id)
      entityDao.remove(taker)
      val lesson = taker.lesson
      businessLogger.info(s"退选了${lesson.course.name}(${lesson.crn}),时间为${lesson.openOn.get} ${lesson.beginAt}", taker.id, Map.empty)
      redirect("index", "退选成功")
    } else {
      redirect("index", "退选失败")
    }
  }

  def detail(): View = {
    val lesson = entityDao.get(classOf[LabourLesson], getLongId("lesson"))
    put("lesson", lesson)
    val syllabus = entityDao.findBy(classOf[LabourCourseSyllabus], "course", lesson.course).headOption.getOrElse(new LabourCourseSyllabus)
    put("syllabus", syllabus)
    forward()
  }
}
