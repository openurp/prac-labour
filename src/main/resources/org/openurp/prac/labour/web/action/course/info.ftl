[@b.head/]
<div class="container-fluid">
  [@b.toolbar title="课程信息"]
    bar.addBack();
  [/@]
  <style>
    .infoTable p{
      margin:0px;
    }
  </style>
  <table class="infoTable">
    <tr>
      <td class="title" width="120px">课程代码：</td>
      <td width="20%">${course.code}</td>
      <td class="title" width="120px">课程名称：</td>
      <td>${course.name}</td>
      <td class="title" width="120px">学时</td>
      <td width="20%">${course.creditHours} [#list course.hours as h]${h.nature.name} ${h.creditHours}[#sep],[/#list]</td>
    </tr>
    <tr>
      <td class="title">开课部门：</td>
      <td>${course.department.name}</td>
      <td class="title">课程分类：</td>
      <td>${course.courseType.name}</td>
      <td class="title">学时</td>
      <td>${course.creditHours}</td>
    </tr>
    <tr>
      <td class="title">课程目标：</td>
      <td colspan="5">${syllabus.objects!}</td>
    </tr>
    <tr>
      <td class="title">课程内容：</td>
      <td colspan="5">${syllabus.contents!}</td>
    </tr>
    <tr>
      <td class="title">教学方法：</td>
      <td colspan="5">${syllabus.methods!}</td>
    </tr>
    <tr>
      <td class="title">课程考核：</td>
      <td colspan="5">${syllabus.assessments!}</td>
    </tr>
    <tr>
      <td class="title">授课对象：</td>
      <td colspan="5">${syllabus.stdScope!}</td>
    </tr>
    <tr>
      <td class="title">任课教师：</td>
      <td colspan="5">${syllabus.teachers!}</td>
    </tr>
    <tr>
      <td class="title">上课地点：</td>
      <td colspan="5">${syllabus.locations!}</td>
    </tr>
    <tr>
      <td class="title">安全须知：</td>
      <td colspan="5">${syllabus.safetyTips!}</td>
    </tr>
  </table>
</div>
[@b.foot/]
