[@b.head/]
[@base.semester_bar value=semester/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="lessonList" title="ui.searchForm" theme="search"]
      [@b.textfield name="lesson.crn" label="课程序号" maxlength="4000"/]
      [@b.textfield name="lesson.course.code" label="课程代码"/]
      [@b.textfield name="lesson.course.name" label="课程名称"/]
      [@b.select name="lesson.teachDepart.id" label="开课院系" items=departs empty="..."/]
      [@b.date name="lesson.openOn" label="上课日期"/]
      [@b.textfield name="lesson.teachers" label="授课老师"/]
      <input type="hidden" name="lesson.semester.id" value="${semester.id}" />
      <input type="hidden" name="orderBy" value="lesson.openOn desc"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="lessonList" href="!search?lesson.semester.id=${semester.id}&orderBy=lesson.openOn desc"/]
    </div>
</div>
[@b.foot/]
