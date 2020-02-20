<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/public/css/bootstrap.min.css" rel="stylesheet">
<link href="/public/css/index.css" rel="stylesheet">
<title>${article.title}</title>
</head>
<script type="text/javascript">
      var articleId = "${id}"
          
</script>
<body>
	<nav class="nav justify-content-start" style="background-color: #222;">
		<c:if test="${USER_SESSION_ID!=null && USER_SESSION_ID.headimg!=null }">
		<a class="nav-link navbar-brand" href="/"> 
			<img src="${USER_SESSION_ID.headimg }"
			width="30" height="30" alt="">
		</a>
	</c:if>
	<c:if test="${USER_SESSION_ID==null || USER_SESSION_ID.headimg==null }">
		<a class="nav-link navbar-brand" href="/"> 
			<img src="https://v4.bootcss.com/docs/4.3/assets/brand/bootstrap-solid.svg"
			width="30" height="30" alt="">
		</a>
	</c:if>
		<c:if test="${USER_SESSION_ID!=null }">
			<a class="nav-link" href="/user/center">发文</a>
			<a class="nav-link" href="/user/center">个人中心</a>
			<a class="nav-link" href="javascript:;">${USER_SESSION_ID.nickname }</a>
			<a class="nav-link" href="/user/logout">退出</a>
		</c:if>
		<c:if test="${USER_SESSION_ID==null }">
			<a class="nav-link" href="/user/login">登录</a>
		</c:if>
	</nav>
	<!-- 详情页主体 -->
	<div class="container-fluid">
		<div class="row offset-1" style="margin-top: 15px;">
			<div class="col-7">
				<h1>${article.title }</h1>
				<!-- 添加一个收藏功能 -->
				<input type="button" value="收藏" onclick="collect()">
				<div style="margin-top: 10px;margin-bottom: 10px;font-weight: bold;">
				<span>${user.nickname }</span> 
					<span><fmt:formatDate value="${article.created }" pattern="yyyy-MM-dd HH:mm"/></span>
				</div>
				<div>
					<div style="font-size: 24">
					${article.content}
					</div>
				</div>
				<!-- 评论框 -->
			 <div id="comment">
					<div class="row" style="margin-top: 10px;">
						  <div class="col-10">
							  <form class="was-validated">
							    <textarea class="form-control" rows="2" id="content" placeholder="请输入评论内容" required></textarea>
							  </form>
						  </div>
						  <div style="margin-top: 10px;">
						    <button type="button" class="btn btn-primary" onclick="addComment();">评论</button>
						  </div>
					</div>
				</div> 
			</div>
             <!-- 详情页右侧 相关文章 -->
			<div class="col-3">
				<div class="right">
					<div>相关文章</div>
					<ul class="list-unstyled">
					<c:forEach items="${articleList}" var="item">
					  <li class="media">
					  <a href="/article/${item.id}.html" ><img style="width: 64px; height: 64px;"	src="${item.picture }"class="mr-3" alt="断网了">
					  </a>
							<div class="media-body">
								<h5 class="mt-0 mb-1"><a href="/article/${item.id}.html" >${item.title} </a></h5>
							</div></li>
					</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div class="modal" tabindex="-1" role="dialog" id="checkModal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">文章收藏</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="checkForm">  
           	
			<p></p>
        	<div class="form-check">
			 请输入你要收藏的url： <input class="form-check-input" type="text" name="url" id="url" onblur="checkUrl()"><br><p></p> <span id="httpUrl" style="color: red"></span>
			</div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="toCheck();">确定</button>
      </div>
    </div>
  </div>
</div>
	<script type="text/javascript" src="/public/js/jquery.min.1.12.4.js"></script>
	<script type="text/javascript" src="/public/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	
	
	function collect(){
		$('#checkModal').modal('show');
	}
	
	function checkUrl() {
	   var url=$("#url").val();
	   $.post("/collect/checkUrl",{url:url},function(res){
		   
		   if(res){
			   $("#httpUrl").html("校验通过");
		   }
		   else{
			   $("#httpUrl").html("您输入的url格式有误，请重新输入");
		   }
	   })
	}
	function toCheck(){
		var data = $('#checkForm').serialize();
		console.log("data:"+data);
		$.post("/collect/add",data,function(res){
			$('#checkModal').modal('hide');
			$('.alert').html("已收藏");
			$('.alert').show();
		});
	}
	</script>
</body>
</html>