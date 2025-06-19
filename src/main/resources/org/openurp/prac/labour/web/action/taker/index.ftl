[@b.head/]
[@base.semester_bar value=semester/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="takerList" title="ui.searchForm" theme="search"]
      [@b.textfield name="taker.std.code" label="学号" maxlength="4000"/]
      [@b.textfield name="taker.std.name" label="姓名"/]
      [@b.textfield name="taker.lesson.crn" label="课程序号" maxlength="4000"/]
      [@b.textfield name="taker.lesson.course.name" label="课程名称"/]
      [@b.select name="taker.lesson.teachDepart.id" label="开课院系" items=departs empty="..."/]
      [@b.date name="taker.lesson.openOn" label="上课日期"/]
      [@b.textfield name="taker.lesson.teachers" label="授课老师"/]
      [@b.select style="width:100px" name="taker.passed" label="是否完成" items={"1":"是", "0":"否","null":"考核中"} empty="..." /]
      <input type="hidden" name="taker.lesson.semester.id" value="${semester.id}" />
      <input type="hidden" name="orderBy" value="taker.lesson.openOn desc"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="takerList" href="!search?taker.lesson.semester.id=${semester.id}&orderBy=taker.lesson.openOn desc"/]
    </div>
</div>
[@b.foot/]
