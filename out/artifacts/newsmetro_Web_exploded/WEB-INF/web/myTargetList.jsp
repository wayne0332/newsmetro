<%@ page language="java" import="java.util.*,com.newsmetro.enumeration.TargetStatus" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <% pageContext.setAttribute("regularEnum", TargetStatus.REGULAR); %>
    <% pageContext.setAttribute("hidenEnum", TargetStatus.HIDE); %>
    <title>NewsMetro 管理信息源</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/base-min.css">
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="shortcut icon" href="img/newsmetro_logo_blue_16.ico" type="image/x-icon" />
  </head>
  
  <body style="font-family:微软雅黑;">
  	<div class="container cf">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="content">
			<div class="p10">
				<div class="fl ml10">
					<span><a class="a_link"
						style="line-height:76px; font-size: 22px;"
						href="<c:url value="/myTargetList.html"/>">管理</a>
					</span>
				</div>
				<div class="fl">
					<a href="javascript:void(0);" id="add_res" class="a_link_guan"
						style="line-height:70px; font-size: 36px; font-weight: bold;">+</a>
				</div>
				<div id="add_form" style="line-height:76px; display:none;">
					<form action="<c:url value='/manage/addResource.html' />" method="post">
						资源名：<input class="w80" name="name" type="text" />
						url：<input class="w240" name="url" type="text" />
					<input type="hidden" name="isRss" value="true" />
						<input type="submit" value="添加" class="signup_btn" />
					</form>
				</div>
				<div class="cb"></div>
			</div>
			<div class="cb" ></div>
			<div class="p10" >
			<ul>
			<c:forEach items="${requestScope.targetList }" var="t">
				<li id="li_<c:out value="${t.id }" />">
					<div class="m10" >
						<div class="fl single_line_text w400">
							<span class="ml10"><c:out value="${t.name}" /></span>
							<span class="ml10 color_gray"><c:out value="${t.url}" /></span>
						</div>
						<div id="show_switch_<c:out value="${t.id}" />" class="fl ml10 single_line_text w30">
						<c:if test="${t.status eq regularEnum }" >
							<span><a class="a_link_guan" href="javascript:hideTarget(<c:out value="${t.id }" />);">隐藏</a></span>
						</c:if>
						<c:if test="${t.status eq hidenEnum }">
							<span><a class="a_link_guan" href="javascript:showUpTarget(<c:out value="${t.id }" />);">显示</a></span>
						</c:if>
						</div>
						<div class="fl ml10 single_line_text w30">
							<span><a class="a_link_guan" href="javascript:deleteTarget(<c:out value="${t.id }" />);">删除</a></span>
						</div>
					</div>
				</li>
			</c:forEach>
			</ul>
			</div>
		</div>
	</div>
    
  </body>
  <script type="text/javascript">
  	function deleteTarget(id){
  		if(confirm("是否确认删除?")){
	  		$.ajax({
				type : "POST",
				url : "deleteTarget.html",
				data: {"id":id},
	   	 		dataType: "json",
				success : function(data) {
					if(data.res == true){
						$("#li_"+id).attr("style","display:none;");
					}else{
						alert("删除失败鸟~");
					}
				}
			});
		}
  	}
  	function hideTarget(id){
	  	$.ajax({
			type : "POST",
			url : "hideTarget.html",
			data: {"id":id},
	   		dataType: "json",
			success : function(data) {
				if(data.res == true){
					$("#show_switch_"+id).html("<span><a class='a_link_guan' href='javascript:showUpTarget("+id+");'>显示</a></span>");
				}else{
					alert("设置失败鸟~");
				}
			}
		});
  	}
  	function showUpTarget(id){
	  	$.ajax({
			type : "POST",
			url : "showUpTarget.html",
			data: {"id":id},
  	 		dataType: "json",
			success : function(data) {
				if(data.res == true){
					$("#show_switch_"+id).html("<span><a class='a_link_guan' href='javascript:hideTarget("+id+");'>隐藏</a></span>");
				}else{
					alert("设置失败鸟~");
				}
			}
		});
  	}
  	
  	/* 点击添加 */
	$('#add_res').click(function(){
		$('#add_form').toggle(0);
	});
  </script>
</html>

