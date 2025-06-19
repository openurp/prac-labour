[@b.head/]
<div class="container">
  [@b.toolbar title="课程信息"]
    bar.addBack();
  [/@]
  [#assign course= lesson.course/]
  <style>
    p{
      margin-bottom: 0rem;
    }
    dd{
      padding-left: 1rem;
    }
  </style>
  <h5 style="text-align:center;">${course.code} ${course.name}</h5>
  <dl>
    <dt>学时：</dt>
    <dd>${course.creditHours}([#list course.hours as h]${h.nature.name} ${h.creditHours}[#sep],[/#list])</dd>
    <dt>上课时间地点：</dt>
    <dd>${lesson.openOn?string('MM-dd')} ${lesson.beginAt}~${lesson.endAt} ${lesson.locations}</dd>
    <dt>开课部门：</dt>
    <dd>${course.department.name}</dd>
    <dt>课程目标：</dt>
    <dd>${syllabus.objects!}</dd>
    <dt>课程内容：</dt>
    <dd>${syllabus.contents!}</dd>
    <dt>教学方法：</dt>
    <dd>${syllabus.methods!}</dd>
    <dt>课程考核：</dt>
    <dd>${syllabus.assessments!}</dd>
    <dt>安全须知：</dt>
    <dd>${syllabus.safetyTips!}</dd>
  </dl>
</div>
[@b.foot/]
