[#ftl]
[@b.head/]
[@b.grid items=takers var="taker"]
  [@b.gridbar]
    //bar.addItem("删除",action.remove());
    bar.addItem("修改",action.edit());
    bar.addItem("批量修改",action.multi('batchEdit'));
    bar.addItem("${b.text("action.export")}",action.exportData("lesson.crn:课程序号,lesson.course.code:课程代码,"+
                "lesson.course.name:课程名称,std.code:学号,std.name:姓名,std.state.department.name:院系,"+
                "std.state.squad.name:班级,std.user.mobile:联系电话,lesson.teachDepart.name:开课院系,lesson.openOn:上课日期,"+
                "lesson.beginAt:开始时间,lesson.endAt:结束时间,lesson.teachers:上课教师,lesson.locations:上课地点,"+
                "lesson.remark:上课备注,passed:是否完成,remark:学生上课情况说明"
                ,null,'fileName=劳动教育上课名单信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="8%" property="std.code" title="学号"/]
    [@b.col width="9%" property="std.name" title="姓名"/]
    [@b.col width="6%" property="lesson.crn" title="课程序号"/]
    [@b.col property="lesson.course.name" title="课程名称"/]
    [@b.col width="6%" property="std.state.department.name" title="院系"]
      ${taker.std.state.department.shortName!taker.std.state.department.name}
    [/@]
    [@b.col width="10%" property="std.state.squad.name" title="班级"]
      ${taker.std.state.squad.shortName!taker.std.state.squad.name}
    [/@]
    [@b.col width="10%" property="lesson.teachers" title="任课教师"/]
    [@b.col width="10%" property="lesson.locations" title="上课地点"/]
    [@b.col width="9%" property="lesson.openOn" title="上课日期"/]
    [@b.col width="9%" property="lesson.beginAt" title="上课时间"]
      ${taker.lesson.beginAt}~${taker.lesson.endAt}
    [/@]
  [/@]
  [/@]
[@b.foot/]
