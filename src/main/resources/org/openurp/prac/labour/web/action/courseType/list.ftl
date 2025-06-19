[@b.head/]
[@b.form name="courseTypeListForm" action="!search"]
  [@b.grid items=courseTypes var="courseType"]
    [@b.gridbar]
      bar.addItem("${b.text("action.new")}",action.add());
      bar.addItem("${b.text("action.modify")}",action.edit());
      bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    [/@]
    [@b.row]
      [@b.boxcol /]
      [@b.col width="15%" property="code" title="代码"]${courseType.code}[/@]
      [@b.col width="20%" property="name" title="名称"]${courseType.name}[/@]
      [@b.col property="enName" title="英文名"]${courseType.enName!}[/@]
      [@b.col width="20%" property="beginOn" title="生效日期"]${courseType.beginOn!}[/@]
      [@b.col width="20%" property="endOn" title="失效日期"]${courseType.endOn!}[/@]
    [/@]
  [/@]
[/@]
[@b.foot/]
