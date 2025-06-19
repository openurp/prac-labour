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

package org.openurp.prac.labour.model

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.time.HourMinute
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.Remark
import org.openurp.base.model.{Department, ProjectBased, Semester}
import org.openurp.base.std.model.Grade

import java.time.{Instant, LocalDate, LocalDateTime}
import scala.collection.mutable

/** 劳动教育课程
 */
class LabourLesson extends LongId, ProjectBased, Remark {

  var crn: String = _

  /** 年级 */
  var grades: mutable.Set[Grade] = Collections.newSet[Grade]
  /** 主题 */
  var subject: Option[String] = None

  var teachDepart: Department = _

  var semester: Semester = _

  var course: LabourCourse = _

  var teachers: Option[String] = _

  var locations: Option[String] = None

  var openOn: Option[LocalDate] = None

  var beginAt: HourMinute = HourMinute.Zero

  var endAt: HourMinute = HourMinute.Zero

  var capacity: Int = _

  var stdCount: Int = _

  var takers: mutable.Set[LabourLessonTaker] = Collections.newSet[LabourLessonTaker]

  var chooseBeginAt: Option[Instant] = None

  var chooseEndAt: Option[Instant] = None

  def this(l: LabourLesson) = {
    this()
    this.crn = l.crn + ("(copy)")
    this.project = l.project
    this.course = l.course
    this.semester = l.semester
    this.teachDepart = l.teachDepart
    this.teachers = l.teachers
    this.locations = l.locations
    this.openOn = l.openOn
    this.beginAt = l.beginAt
    this.endAt = l.endAt
    this.chooseBeginAt = l.chooseBeginAt
    this.chooseEndAt = l.chooseEndAt
    this.capacity = l.capacity
  }

  def openAt: Option[LocalDateTime] = {
    openOn.map(_.atTime(beginAt.toLocalTime))
  }
}
