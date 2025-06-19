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

package org.openurp.prac.labour.web.helper

import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.doc.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.model.{Project, Semester}
import org.openurp.base.std.model.Grade
import org.openurp.prac.labour.model.LabourLesson

import java.time.{Instant, ZoneId}

class LessonImportListener(entityDao: EntityDao, project: Project, semester: Semester) extends ImportListener {

  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("lesson.crn") foreach { code =>
      val query = OqlBuilder.from(classOf[LabourLesson], "c")
      query.where("c.crn =:crn and c.project=:project and c.semester=:semester", code, project, semester)
      val cs = entityDao.search(query)
      if (cs.nonEmpty) transfer.current = cs.head
      else
        val nl = new LabourLesson
        nl.project = project
        nl.semester = semester
        transfer.current = nl
    }
    transfer.curData.get("lesson.beginAt") foreach { b =>
      transfer.curData.put("lesson.beginAt", b.asInstanceOf[String].replace("：", ":"))
    }
    transfer.curData.get("lesson.endAt") foreach { b =>
      transfer.curData.put("lesson.endAt", b.asInstanceOf[String].replace("：", ":"))
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val lesson = transfer.current.asInstanceOf[LabourLesson]
    lesson.teachDepart = lesson.course.department
    transfer.curData.get("grades") foreach { grades =>
      val gs = entityDao.findBy(classOf[Grade], "project" -> lesson.course.project, "code" -> Strings.split(Strings.replace(grades.toString, "，", ",")))
      lesson.grades.clear()
      lesson.grades.addAll(gs)
    }
    if (lesson.chooseEndAt.isEmpty && lesson.openOn.nonEmpty) {
      lesson.chooseEndAt = Some(lesson.openOn.get.minusDays(1).atTime(20, 0, 0).atZone(ZoneId.systemDefault).toInstant)
      if (lesson.chooseBeginAt.isEmpty) {
        lesson.chooseBeginAt = Some(Instant.now)
      }
    }
    entityDao.saveOrUpdate(lesson)
  }
}
