[#ftl]
[@b.head/]
<div class="container" style="width:95%">
  <nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="#">校内服务性劳动教育</a>
      </div>
    </div>
  </nav>
  [@b.messages slash="4"/]

  [@b.card class="card-info card-outline"]
    [@b.card_header style="padding-bottom: 0px;"]<h5>我的课程</h5>[/@]
    <div class="card-body" style="padding-top: 0px;">
      [#if takers?size>0]
      <table class="table table-hover table-sm">
        <thead>
          <tr>
            <th>课程</th>
            <th>时间</th>
            <th>地点</th>
            <th>教师</th>
            <th style="max-width:30%;">备注</th>
          </tr>
        </thead>
        <tbody>
        [#list takers?sort_by(["lesson","openOn"]) as taker]
        <tr>
          <td>[@b.a href="!detail?lesson.id=${taker.lesson.id}"]${taker.lesson.course.name}[/@]</td>
          <td>${taker.lesson.openOn?string('MM-dd')} ${taker.lesson.beginAt}~${taker.lesson.endAt}</td>
          <td>${taker.lesson.locations!}</td>
          <td>${taker.lesson.teachers!}</td>
          <td>
            [#if taker.lesson.openOn?date > b.now?date]
              [@b.a href="!drop?taker.id=${taker.id}"]退课[/@]
            [/#if]
          </td>
        </tr>
        [#if taker.lesson.remark?? && taker.lesson.remark?length>0]
        <tr>
          <td class="text-muted" colspan="5" style="font-size:0.8rem;">${taker.lesson.remark}</td>
        </tr>
        [/#if]
        [/#list]
        </tbody>
      </table>
      [#else]
      你尚未选择任何课程。
      [/#if]
    </div>
  [/@]
    <div style="background-color: #e9ecef;border-radius: .3rem;padding: 1rem 2rem;">
      <h4>可选课程</h4>
      <div>请浏览以下分类课程，进行选择。</div>
    </div>
[#list lessons?keys as category]
  [@b.card class="card-info card-outline"]
    [@b.card_header style="padding-bottom: 0px;"]<h5>${category.name}</h5>[/@]
    <div class="card-body" style="padding-top: 0px;">
      <table class="table table-hover table-sm">
        <thead>
          <tr>
            <th>课程</th>
            <th>时间</th>
            <th>地点</th>
            <th>容量</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
        [#list lessons.get(category)?sort_by("openAt") as lsn]
        <tr>
          <td>[@b.a href="!detail?lesson.id=${lsn.id}"]${lsn.course.name}[/@]</td>
          <td>${lsn.openOn?string('MM-dd')} ${lsn.beginAt}~${lsn.endAt}</td>
          <td>${lsn.locations!}</td>
          <td>${lsn.stdCount}/${lsn.capacity}</td>
          <td>
            [#if takerLessons.get(lsn)??]
              [@b.a href="!drop?taker.id=${takerLessons.get(lsn).id}"]退课[/@]
            [#else]
              [#if lsn.capacity>lsn.stdCount]
              [@b.a href="!choose?lesson.id=${lsn.id}"]选择[/@]
              [/#if]
            [/#if]
          </td>
        </tr>
        [/#list]
        </tbody>
      </table>
    </div>
  [/@]
[/#list]

</div>

[@b.foot/]
