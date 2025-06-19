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

import org.beangle.webmvc.annotation.{mapping, param}
import org.beangle.webmvc.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.model.{Department, Project}
import org.openurp.code.edu.model.TeachingNature
import org.openurp.prac.labour.model.{LabourCourse, LabourCourseHour, LabourCourseSyllabus, LabourCourseType}
import org.openurp.starter.web.support.ProjectSupport

import java.time.LocalDate

class CourseAction extends RestfulAction[LabourCourse], ProjectSupport {

  override protected def simpleEntityName: String = "course"

  protected override def indexSetting(): Unit = {
    given project: Project = getProject

    put("courseTypes", getCodes(classOf[LabourCourseType]))
    val departments = findInSchool(classOf[Department])
    put("departments", departments)
  }

  protected override def editSetting(c: LabourCourse): Unit = {
    given project: Project = getProject

    put("courseTypes", getCodes(classOf[LabourCourseType]))
    put("departments", findInSchool(classOf[Department]))

    put("teachingNatures", getCodes(classOf[TeachingNature]))
    put("project", project)
    if (c.persisted) {
      put("syllabus", entityDao.findBy(classOf[LabourCourseSyllabus], "course", c).headOption.getOrElse(new LabourCourseSyllabus))
    } else {
      c.beginOn = LocalDate.now.minusDays(1)
      put("syllabus", new LabourCourseSyllabus)
    }
    super.editSetting(c)
  }

  def copySetting(): View = {
    given project: Project = getProject

    val from = entityDao.get(classOf[LabourCourse], getLongId("course"))
    val course = new LabourCourse(from)
    val fromSyllabus = entityDao.findBy(classOf[LabourCourseSyllabus], "course", from).headOption.getOrElse(new LabourCourseSyllabus)
    val syllabus = new LabourCourseSyllabus(fromSyllabus)
    put("course", course)
    put("syllabus", syllabus)
    put("courseTypes", getCodes(classOf[LabourCourseType]))
    put("departments", findInSchool(classOf[Department]))

    put("teachingNatures", getCodes(classOf[TeachingNature]))
    put("project", project)
    forward("form")
  }

  protected override def saveAndRedirect(course: LabourCourse): View = {
    given project: Project = getProject

    val teachingNatures = getCodes(classOf[TeachingNature])
    teachingNatures foreach { ht =>
      val creditHour = getFloat("creditHour" + ht.id)
      val week = getInt("week" + ht.id)
      course.hours find (h => h.nature == ht) match {
        case Some(hour) =>
          if (week.isEmpty && creditHour.isEmpty) {
            course.hours -= hour
          } else {
            hour.creditHours = creditHour.getOrElse(0f)
          }
        case None =>
          if (!(week.isEmpty && creditHour.isEmpty)) {
            val newHour = new LabourCourseHour(course, ht, creditHour.getOrElse(0f))
            course.hours += newHour
          }
      }
    }
    val orphan = course.hours.filter(x => !teachingNatures.contains(x.nature))
    course.hours --= orphan

    course.project = project
    entityDao.saveOrUpdate(course)

    val syllabus = entityDao.findBy(classOf[LabourCourseSyllabus], "course", course).headOption.getOrElse(new LabourCourseSyllabus)
    syllabus.course = course
    populate(syllabus, "syllabus")
    entityDao.saveOrUpdate(syllabus)

    super.saveAndRedirect(course)
  }

  @mapping(value = "{id}")
  override def info(@param("id") id: String): View = {
    val course = entityDao.get(classOf[LabourCourse], id.toLong)
    put("course", course)
    val syllabus = entityDao.findBy(classOf[LabourCourseSyllabus], "course", course).headOption.getOrElse(new LabourCourseSyllabus)
    put("syllabus", syllabus)
    forward()
  }
}
