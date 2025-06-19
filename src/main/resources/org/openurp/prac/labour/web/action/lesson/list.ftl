[#ftl]
[@b.head/]
[@b.grid items=lessons var="lesson"]
  [@b.gridbar]
    bar.addItem("添加",action.add());
    bar.addItem("修改",action.edit());
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("删除",action.remove());
    bar.addItem("${b.text("action.export")}",action.exportData("crn:课程序号,course.code:课程代码,course.name:课程名称,"+
                "teachDepart.name:开课院系,openOn:上课日期,beginAt:开始时间,endAt:结束时间,teachers:上课教师,locations:上课地点," +
                "capacity:人数上限,stdCount:已选人数,"+
                "chooseBeginAt:选课开始时间,chooseEndAt:选课结束时间",null,'fileName=劳动教育开课信息'));
    var m = bar.addMenu("高级...")
    m.addItem("统计人数",action.method('stat'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="6%" property="crn" title="课程序号"/]
    [@b.col width="8%" property="course.code" title="课程代码"/]
    [@b.col property="course.name" title="课程名称"/]
    [@b.col width="5%" property="course.creditHours" title="学时"/]
    [@b.col width="12%" property="teachDepart.name" title="开课部门"]
      ${lesson.teachDepart.shortName!lesson.teachDepart.name}
    [/@]
    [@b.col width="10%" property="teachers" title="任课教师"/]
    [@b.col width="10%" property="locations" title="上课地点"/]
    [@b.col width="9%" property="openOn" title="上课日期"/]
    [@b.col width="9%" property="beginAt" title="上课时间"]
      ${lesson.beginAt}~${lesson.endAt}
    [/@]
    [@b.col width="4%" property="stdCount" title="人数"/]
    [@b.col width="4%" property="capacity" title="上限"/]
  [/@]
  [/@]
[@b.foot/]
