[#ftl]
[@b.head/]
[@b.toolbar title="批量修改学生上课信息"]bar.addBack();[/@]
[#assign taker = takers?first/]
[@b.form action="!batchUpdate" theme="list"]
  [@b.field label="学年学期"]${taker.lesson.semester.schoolYear}学年度${taker.lesson.semester.name}学期[/@]
  [@b.field label="上课记录"]
    <div style="margin-left: 100px;">
    ${takers?size}条上课记录<br>
    [#list takers as taker]
    ${taker_index+1}. ${taker.std.code} ${taker.std.name} ${taker.lesson.crn} ${taker.lesson.course.name} ${taker.lesson.openOn!} ${taker.lesson.beginAt!}<br>
    [/#list]
    </div>
  [/@]
  [@b.radios name="passed" value="" label="是否完成" items={"1":"是", "0":"否","":"保持不变"} required="true"/]
  [@b.textarea name="remark" label="备注" required="false" maxlength="300" rows="4" cols="70"/]
  [@b.formfoot]
      [#list takers as taker]
      <input type="hidden" name="taker.id" value="${taker.id}"/>
      [/#list]
    [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
  [/@]
[/@]
[@b.foot/]
