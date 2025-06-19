[#ftl]
[@b.head/]
[#include "../course/nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
      [@b.form name="courseSearchForm" action="!search" target="courseTypeList" title="ui.searchForm" theme="search"]
        [@b.textfield name="courseType.code" label="代码" maxlength="5000"/]
        [@b.textfields names="courseType.name;名称"/]
        [@b.select name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." value="1" /]
      [/@]
    </div>
    <div class="search-list">[@b.div id="courseTypeList" href="!search"/]
  </div>
</div>
[@b.foot/]
