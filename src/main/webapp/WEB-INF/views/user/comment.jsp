<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cms后台登录</title>
<link href="/public/css/bootstrap.min.css" rel="stylesheet">
<link href="/public/css/cms.css" rel="stylesheet">
</head>
<body>
  	<form class="form-inline" id="queryForm">
	  <div class="form-group mx-sm-3 mb-2">
	    <input type="text" name="title" class="form-control" value="${comment.title}" placeholder="请输入文章标题">
	  </div>
	  <div class="form-group mx-sm-3 mb-2">
	      <input type="date" name="created" id="created" class="form-control" value="${comment.created}" placeholder="请输入评论时间">
	  </div>
	  <div class="form-group mx-sm-3 mb-2">
	   <input type="text" name="content" id="content" class="form-control" value="${comment.content}" placeholder="请输入评论内容">
	  <input type="hidden" name="pageNum" value="1">
	  </div>
	  <button type="button" class="btn btn-primary mb-2" onclick="query()">查询</button>
	</form>
  	<table class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">文章标题</th>
       <th scope="col">评论时间</th>
      <th scope="col">评论内容</th>
       <th scope="col">操作</th>
    </tr>
  </thead>
  <tbody>
     <c:forEach items="${pageInfo.list}" var="item">
      <tr>
      <th scope="row">${item.id }</th>
      <td>${item.title }</td>
      <td>${item.created }</td>
      <td>${item.content }</td>
      <td><input type="button" value="删除" onclick="del(${item.id})"></td>
      </tr>
     </c:forEach>  
  </tbody>
</table>
<div class="row">
	<div class="col-10">
		<jsp:include page="../common/page.jsp"></jsp:include>
	</div>
</div>
<script type="text/javascript" src="/public/js/bootstrap.min.js"></script>
<script type="text/javascript">
	//分页查询
	function gotoPage(pageNo){
		$("[name=pageNum]").val(pageNo);
		query();
	}
	 //查询方法
	function query(){
		var params = $("form").serialize();
		reload(params);
	}
	//删除该条评论
	function del(id){
		if(confirm("您确定要删除该条评论吗？")){
			$.get("/comment/del",{id:id},function(res){
				  if(res.result){
					  alert("删除成功");
					  query();
				  }
				  else{
					  alert("删除失败");
				  }
			});
		}
		
	}
</script>
</body>
</html>