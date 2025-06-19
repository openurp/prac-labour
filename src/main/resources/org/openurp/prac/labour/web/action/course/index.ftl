[#ftl]
[@b.head/]
[#include "nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
      [@b.form name="courseSearchForm" action="!search" target="courselist" title="ui.searchForm" theme="search"]
        [@b.textfield name="course.code" label="代码" maxlength="5000"/]
        [@b.textfields names="course.name;名称"/]
        [@b.select name="course.courseType.id" label="课程类别" empty="..." /]
        [@b.select name="course.department.id" label="所属部门" items=departments option="id,name" empty="..." /]
        [@b.select name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." value="1" /]
        <input type="hidden" name="orderBy" value="course.code"/>
      [/@]
    </div>
    <div class="search-list">[@b.div id="courselist" href="!search?orderBy=course.code asc&active=1"/]
  </div>
</div>
[@b.foot/]
