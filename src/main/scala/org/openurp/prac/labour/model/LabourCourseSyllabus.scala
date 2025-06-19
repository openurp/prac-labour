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

import org.beangle.data.model.LongId

/** 劳动教育课程大纲
 */
class LabourCourseSyllabus extends LongId {

  var course: LabourCourse = _

  var contents: String = _

  var objects: String = _

  var methods: String = _

  var assessments: String = _

  var stdScope: String = _

  var safetyTips: Option[String] = None

  var teachers: Option[String] = None

  var locations: Option[String] = None

  def this(s: LabourCourseSyllabus) = {
    this()
    this.contents = s.contents
    this.objects = s.objects
    this.methods = s.methods
    this.assessments = s.assessments
    this.stdScope = s.stdScope
    this.safetyTips = s.safetyTips
    this.teachers = s.teachers
    this.locations = s.locations
  }

}
