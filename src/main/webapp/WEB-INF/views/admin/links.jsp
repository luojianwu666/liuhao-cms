<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
  	<form class="form-inline" id="queryForm">
	  <div class="form-group mx-sm-3 mb-2">
	    <input type="text" name="text" class="form-control" placeholder="请输入名称">
	  </div>
	  <input type="hidden"  id ="pageNum"  name="pageNum" value="${pageInfo.pageNum }">
	  <button type="button" class="btn btn-primary mb-2" onclick="query()">查询</button>
	</form>
  	<table class="table">
  <thead>
    <tr>
      <th scope="col"><input type="checkbox" value="" id="chkALL" name="chkALL"></th>
      <th scope="col">#</th>
      <th scope="col">名称</th>
      <th scope="col">URL</th>
      <th scope="col">创建时间</th>
      <th scope="col">操作</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${pageInfo.list }" var="item">
     <tr>
      <th scope="row" ><input type="checkbox" value="${item.id }" name="chk_list"></th>
      <td>${item.id }</td>
      <td>${item.text}</td>
      <td>${item.url }</td>
      <td>
        <fmt:formatDate value="${item.created }" pattern="yyyy-MM-dd HH:mm:ss"/>
      </td>
      <td>
        <button type="button" class="btn btn-primary" onclick="del('${item.id}')">删除</button>
	  	<button type="button" class="btn btn-primary" onclick="edit('${item.id}')">编辑</button>
      </td>
    </tr>
  </c:forEach>
  </tbody>
  </table>
  <div class="alert alert-danger" role="alert" style="display: none"></div>
  <div class="row">
   <div class="col-2">
     <button type="button" class="btn btn-primary" onclick="add()">添加</button>
     <button type="button" class="btn btn-primary" onclick="batchDel()">批量删除</button>
   </div>
   <div class="col-10">
    <jsp:include page="../common/page.jsp"></jsp:include>
   </div>
  </div>
  <div class="modal" tabindex="-1" role="dialog" id="delModal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">删除确认框</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
                   你确认删除选择的数据吗？
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="dele();">确定删除</button>
      </div>
    </div>
  </div>
</div>
  
<script type="text/javascript" src="/public/js/checkbox.js"></script>
<script>
        function edit(linksId){
        	$.get("/admin/links/edit?linksId="+linksId,{},function(res){
        		$(".tab-content").html(res);
        	},"html")
        	//reload();
        }
        function query(){
        	var data = $("form").serialize();
        	reload(data);
        }
        function add(){
        	openPage("/admin/links/edit");
        	
        }
        function gotoPage(pageNum){
        	$("[name=pageNum]").val(pageNum);
        	query();
        }
        //点击批量删除按钮， 显示模态框
        function batchDel(){
        	//验证
        	var ids = getCheckboxIds();
        	if(ids==""){
        		$(".alert").html("请选中要删除的数据");
        		$(".alert").show();
        		return;
        	}
        	$(".alert").hide();
        	$("#delModal").modal('show');
        }
        //批量删除操作
        function dele(){
        	var ids =getCheckboxIds();
        	$.get("/admin/links/delByIds",{ids:ids},function(res){
        		if(res.result){
        			$("#queryForm #pageNum").val(1);
        			$('#delModal').modal('hide');
        			query();
        		}
        		else{
        			$(".alert").html(res.message);
            		$(".alert").show();
        			$("#delModal").modal('hide');
        			
        		}
        	})
        }
        
</script>