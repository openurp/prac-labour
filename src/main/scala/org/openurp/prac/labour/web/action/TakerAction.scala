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

import org.beangle.webmvc.view.View
import org.beangle.webmvc.support.action.{ExportSupport, RestfulAction}
import org.openurp.base.model.Project
import org.openurp.prac.labour.model.LabourLessonTaker
import org.openurp.starter.web.support.ProjectSupport

class TakerAction extends RestfulAction[LabourLessonTaker], ProjectSupport, ExportSupport[LabourLessonTaker] {

  override protected def simpleEntityName: String = "taker"

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("project", project)
    val semester = getSemester
    put("semester", semester)
    super.indexSetting()
  }

  def batchEdit(): View = {
    val takers = entityDao.find(classOf[LabourLessonTaker], getLongIds("taker"))
    put("takers", takers)
    forward()
  }

  def batchUpdate(): View = {
    val takers = entityDao.find(classOf[LabourLessonTaker], getLongIds("taker"))

    println(getBoolean("passed"))
    getBoolean("passed") foreach { passed =>
      takers.foreach(taker => taker.passed = Some(passed))
    }
    get("remark") foreach { remark =>
      takers.foreach(taker => taker.remark = Some(remark))
    }
    entityDao.saveOrUpdate(takers)
    redirect("search", "设置完成")
  }
}
