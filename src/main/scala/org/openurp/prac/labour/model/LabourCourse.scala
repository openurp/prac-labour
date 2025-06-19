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
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.{Coded, Named, TemporalOn}
import org.openurp.base.model.{Department, ProjectBased}
import org.openurp.code.edu.model.TeachingNature

/** 劳动教育课程
 */
class LabourCourse extends LongId, ProjectBased, Coded, Named, TemporalOn {

  var creditHours: Int = _

  var department: Department = _

  /** 分类课时 */
  var hours = Collections.newBuffer[LabourCourseHour]

  var courseType: LabourCourseType = _

  def this(c: LabourCourse) = {
    this()
    this.code = c.code + "(copy)"
    this.name = c.name + "(副本)"
    this.beginOn = c.beginOn
    this.creditHours = c.creditHours
    this.department = c.department
    this.courseType = c.courseType
    c.hours foreach { h =>
      this.hours += new LabourCourseHour(this, h.nature, h.creditHours)
    }
  }
}

class LabourCourseHour extends LongId {
  def this(course: LabourCourse, nature: TeachingNature, creditHours: Float) = {
    this()
    this.course = course
    this.nature = nature
    this.creditHours = creditHours
  }

  var course: LabourCourse = _
  var creditHours: Float = _
  var nature: TeachingNature = _
}
