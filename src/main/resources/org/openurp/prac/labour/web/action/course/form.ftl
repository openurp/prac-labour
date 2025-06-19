[#ftl]
[@b.head/]
[@b.toolbar title="修改课程"]bar.addBack();[/@]
    [@b.form action=b.rest.save(course) theme="list" onsubmit="validCreditHour"]
      [@b.textfield name="course.code" label="代码" value="${course.code!}" required="true" maxlength="20"/]
      [@b.textfield name="course.name" label="名称" value="${course.name!}" required="true" maxlength="100"/]
      [@b.select name="course.courseType.id" label="课程类别" value=course.courseType! items=courseTypes required="true" empty="..."/]
      [@b.select name="course.department.id" label="部门" value=course.department! required="true"
                 style="width:200px;" items=departments option="id,name" empty="..."/]
      [@b.textfield name="course.creditHours" label="学时" value=course.creditHours! required="true"  maxlength="100"/]
      [#if teachingNatures?size>0]
      [@b.field label="分类课时"]
         [#assign hours={}/]
         [#list course.hours as h]
            [#assign hours=hours+{'${h.nature.id}':h} /]
         [/#list]
         [#list teachingNatures as ht]
          <label for="teachingNature${ht.id}_p">${ht_index+1}.${ht.name}</label>
          <input name="creditHour${ht.id}" style="width:30px" id="teachingNature${ht.id}_p" value="${(hours[ht.id?string].creditHours)!}">课时
         [/#list]
      [/@]
      [/#if]
      [@b.editor name="syllabus.objects" label="课程目标" value=syllabus.objects! maxlength="1000" theme="mini" required="true"/]
      [@b.editor name="syllabus.contents" label="课程内容" value=syllabus.contents! maxlength="1000" theme="mini" required="true"/]
      [@b.editor name="syllabus.methods" label="教学方法" value=syllabus.methods! maxlength="1000" theme="mini" required="true"/]
      [@b.editor name="syllabus.assessments" label="课程考核" value=syllabus.assessments! maxlength="1000" theme="mini" required="true"/]
      [@b.editor name="syllabus.stdScope" label="授课对象" value=syllabus.stdScope!  theme="mini" required="true"/]
      [@b.editor name="syllabus.teachers" label="任课教师" value=syllabus.teachers!  theme="mini"/]
      [@b.editor name="syllabus.locations" label="上课地点" value=syllabus.locations! theme="mini"/]
      [@b.editor name="syllabus.safetyTips" label="安全须知" value=syllabus.safetyTips! maxlength="1000" theme="mini" required="true"/]

      [@b.startend label="有效期"  name="course.beginOn,course.endOn" required="true,false"
        start=course.beginOn end=course.endOn format="date" style="width:100px"/]
      [@b.textarea name="course.remark" label="备注" value="${course.remark!}"  maxlength="100"/]
      [@b.formfoot]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
      [/@]
    [/@]
  <script>
     function validCreditHour(form){
        [#if teachingNatures?size>0]
        var sumCreditHours=0;
        [#list teachingNatures as ht]
        sumCreditHours += Number.parseFloat(form['creditHour${ht.id}'].value||'0');
        [/#list]
        if(sumCreditHours != Number.parseFloat(form['course.creditHours'].value||'0')){
           alert("分类课时总和"+sumCreditHours+",不等于课程学时"+form['course.creditHours'].value);
           return false;
        }else{
           return true;
        }
        [#else]
        return true;
        [/#if]
     }
  </script>
[@b.foot/]
