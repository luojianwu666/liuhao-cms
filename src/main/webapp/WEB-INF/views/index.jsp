<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/public/css/bootstrap.min.css" rel="stylesheet">
<link href="/public/css/index.css" rel="stylesheet">
<style type="text/css">
	
</style>
<title>前台首页</title>
</head>
<body>
	<nav class="nav justify-content-start" style="background-color: #222;">
		<c:if test="${USER_SESSION_ID!=null && USER_SESSION_ID.headimg!=null }">
			<a class="nav-link navbar-brand" href="#">
				<img src="${USER_SESSION_ID.headimg }" width="30" height="30" alt="">
			</a>
		</c:if>
		<c:if test="${USER_SESSION_ID==null || USER_SESSION_ID.headimg==null  }">
			<a class="nav-link navbar-brand" href="#">
				<img src="https://v4.bootcss.com/docs/4.3/assets/brand/bootstrap-solid.svg" width="30" height="30" alt="">
			</a>
		</c:if>
		<!-- 如果session中的值不为空，则有以下内容 -->
		<c:if test="${USER_SESSION_ID!=null }">
			<a class="nav-link" href="/user/center">发文</a> 
			<a class="nav-link" href="/user/center">个人中心</a> 
			<a class="nav-link" href="javascript:;">${USER_SESSION_ID.nickname }</a>
			<a class="nav-link" href="/user/logout">退出</a>
		</c:if>
		<!-- 如果session中的值为空，则只有登录 -->
		<c:if test="${USER_SESSION_ID==null }">
			<a class="nav-link" href="/user/login">登录</a>
		</c:if>
	</nav>
	<div class="container-fluid">
		<div class="row offset-1" style="margin-top: 15px;">
			<div class="col-1">
				<!-- 左侧导航 -->
				<ul class="nav flex-column">
				<li class="nav-item">
				<!-- 频道id为null时，选择热点栏目  回到首页-->
				    <a class="nav-link  <c:if test="${channelId==null }">select</c:if>" href="/">热点</a>
				  </li>
				<c:forEach items="${channelList}" var="item">
				<li class="nav-item">
				<!-- 频道id==频道集合中的频道id  时，选择热点栏目 -->
				    <a class="nav-link <c:if test="${channelId==item.id }">select</c:if>" href="/${item.id}/0/1.html">${item.name }</a>
				  </li>
				</c:forEach>
				</ul>
			</div>
			<div class="col-6">
				<!-- 轮播图 -->
				<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
				  <div class="carousel-inner">
				  <c:forEach items="${slideList }" var="item" varStatus="s">
				  <!-- 默认轮播图为第一张 -->
				  <div class="carousel-item <c:if test="${s.index==0}">active</c:if>" >
				  <a href="${item.url }"><img src="${item.picture }" class="d-block w-100" alt="断网了" style="width: 300px;height: 400px;"></a>
				    </div>
				  </c:forEach>
				  </div>
				  <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
				    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
				    <span class="sr-only">Previous</span>
				  </a>
				  <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
				    <span class="carousel-control-next-icon" aria-hidden="true"></span>
				    <span class="sr-only">Next</span>
				  </a>
				</div>
				<!-- 该频道下有分类则显示 -->	
				<c:if test="${cateList.size()>0 }">
				
				 <ul class="nav nav-tabs">
				  <li class="nav-item ">
				    <a class="nav-link <c:if test="${cateId==0}"> active</c:if>" href="/${channelId }/0/1.html">全部</a>
				  </li>
				  <c:forEach items="${cateList }" var="item">
						  <li class="nav-item">
						    <a class="nav-link <c:if test="${cateId==item.id }">active</c:if>" href="/${channelId }/${item.id }/1.html">${item.name }</a>
						  </li>
					  </c:forEach>
				</ul>
				</c:if>
				<div style="margin-top: 18px;">
				<!-- 搜索框 -->
				<form action="" method="get">
				   <div class="input-group mb-3">
					<input type="text" name="title" id ="title" value="${title}" class="form-control"
						placeholder="请输入要搜索的内容"
						aria-label="Recipient's username" aria-describedby="button-addon2">
					<div class="input-group-append">
						<input type="button" value="搜索" onclick="query()">
					</div>
				</div>
				</form>
				<c:forEach items="${pageInfo.list}" var="item" >
				<div class="media">
					  <img src="${item.picture}" class="mr-3" alt="断网了">
					  <div class="media-body">
					    <h4 class="mt-1">
					    	<a href="/article/${item.id}.html" target="_blank">${item.title}</a>
					    </h4>
					    <p style="color: #999;">${item.nickname }  <fmt:formatDate value="${item.created}" pattern="yyyy-MM-dd HH:mm"/></p>
					  </div>
					</div>
				</c:forEach>
				</div>
				<div style="text-align: center;">
					<jsp:include page="common/page.jsp"></jsp:include>
				</div>
			</div>
			<div class="col-3">
				<div class="right">
					<div>最新文章</div>
					<ul class="list-unstyled">
					<c:forEach items="${newArticleList}" var="item">
					    <li class="media">
					  <a href="article/${item.id}.html" target="_blank">
					  <img style="width: 64px;height: 64px;" src="${item.picture}" class="mr-3" alt="断网了"></a>  
					    <div class="media-body">
					      <h5 class="mt-0 mb-1"><a href="/article/${item.id}.html" target="_blank">${item.title}</a> </h5>
					    </div>
					  </li> 
					</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<p class="nav justify-content-center" style="background-color: #222;">
		<a class="nav-link" href="javascript:;">友情链接</a>
	</p>
	<div class="justify-content-center" style="margin-bottom: 200px;text-align: center;">
			<c:forEach items="${linksList}" var="item" >
			<a href="${item.url}" style="padding-right: 36px;">${item.text }</a>
			</c:forEach>
	</div>
	<script type="text/javascript" src="/public/js/jquery.min.1.12.4.js"></script>
	<script type="text/javascript" src="/public/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	function gotoPage(pageNo){
		//频道id等于空则表示是热点标签页
		if("${channelId}"==0){
			var title = $("#title").val();
			window.location.href="/hot/"+pageNo+".html?title="+title;
		}
		//否则是其他标签页
		else{
			window.location.href="/${channelId}/${cateId}/"+pageNo+".html";
		}
		
	}
	function query(){
		var title = $("#title").val();
		window.location.href="/hot/1.html?title="+title;
	}
	</script>
</body>
</html>