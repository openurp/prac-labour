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

import org.beangle.data.orm.MappingModule

class DefaultMapping extends MappingModule {
  def binding(): Unit = {
    bind[LabourCourse] declare { e =>
      e.hours is depends("course")
    }
    bind[LabourCourseSyllabus] declare { e =>
      e.contents is length(4000)
      e.objects is length(4000)
      e.methods is length(2000)
      e.assessments is length(2000)
      e.safetyTips is length(10000)
      e.teachers is length(500)
      e.locations is length(1000)
    }

    bind[LabourCourseHour]
    bind[LabourCourseType]
    bind[LabourLessonTaker]

    bind[LabourLesson] declare { e =>
      e.crn is length(10)
      e.subject is length(200)
      e.remark is length(300)
      e.takers is depends("lesson")
    }
  }
}
