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

import org.beangle.commons.activation.MediaTypes
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.openurp.base.model.{Project, Semester}
import org.openurp.base.std.model.Grade
import org.openurp.prac.labour.model.{LabourCourse, LabourLesson}
import org.openurp.prac.labour.web.helper.LessonImportListener
import org.openurp.starter.web.support.ProjectSupport

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class LessonAction extends RestfulAction[LabourLesson], ProjectSupport, ExportSupport[LabourLesson], ImportSupport[LabourLesson] {

  override protected def simpleEntityName: String = "lesson"

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("project", project)
    val semester = getSemester
    put("semester", semester)
    super.indexSetting()
  }

  def copySetting(): View = {
    given project: Project = getProject

    put("project", project)
    val from = entityDao.get(classOf[LabourLesson], getLongId("lesson"))
    put("courses", entityDao.findBy(classOf[LabourCourse], "project", project))
    put("lesson", new LabourLesson(from))
    forward("form")
  }

  override protected def editSetting(lesson: LabourLesson): Unit = {
    given project: Project = getProject

    put("project", project)
    put("grades", entityDao.findBy(classOf[Grade], "project", project))
    put("courses", entityDao.findBy(classOf[LabourCourse], "project", project))
    if (!lesson.persisted) lesson.semester = getSemester
    super.editSetting(lesson)
  }

  override protected def saveAndRedirect(lesson: LabourLesson): View = {
    lesson.project = getProject
    if (!lesson.persisted) {
      lesson.teachDepart = entityDao.get(classOf[LabourCourse], lesson.course.id).department
    }
    lesson.grades.clear()
    lesson.grades.addAll(entityDao.find(classOf[Grade], getLongIds("grade")))
    super.saveAndRedirect(lesson)
  }

  def stat(): View = {
    val project = getProject
    val semester = entityDao.get(classOf[Semester], getIntId("lesson.semester"))
    val query = s"update ${classOf[LabourLesson].getName} lesson set stdCount = size(lesson.takers) where lesson.semester.id=${semester.id} and lesson.project.id=${project.id}"
    entityDao.executeUpdate(query)
    redirect("search", "统计成功")
  }

  @response
  def downloadTemplate(): Any = {
    val project = getProject
    val grades = entityDao.search(OqlBuilder.from(classOf[Grade], "g").where("g.project=:project", project).orderBy("g.name")).map(x => x.name)
    val courses = entityDao.search(OqlBuilder.from(classOf[LabourCourse], "bt").orderBy("bt.name")).map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("劳动教育开课信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("课程序号", "lesson.crn").length(4).required().remark("4位")
    sheet.add("课程代码", "lesson.course.code").required().ref(courses)
    sheet.add("教学主题", "lesson.subject").remark("可选")
    sheet.add("上课年级", "grades").ref(grades)
    sheet.add("任课教师", "lesson.teachers").length(100)
    sheet.add("上课地点", "lesson.locations").length(200)
    sheet.add("上课日期", "lesson.openOn").date()
    sheet.add("上课时间", "lesson.beginAt").length(5).remark("HH:mm")
    sheet.add("下课时间", "lesson.endAt").length(5).remark("HH:mm")
    sheet.add("人数上限", "lesson.capacity").integer()
    sheet.add("选课开始", "lesson.chooseBeginAt").datatime().remark("yyyy-MM-dd HH:mm:ss")
    sheet.add("选课结束", "lesson.chooseEndAt").datatime().remark("yyyy-MM-dd HH:mm:ss")
    sheet.add("备注", "lesson.remark")
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx, "劳动教育开课模板.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    val semester = entityDao.get(classOf[Semester], getIntId("lesson.semester"))
    setting.listeners = List(fl, new LessonImportListener(entityDao, getProject, semester))
  }
}
