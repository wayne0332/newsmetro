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
	  <style type="text/css">
		  li {list-style-type:none;}
	  </style>
  </head>
  
  <body style="font-family:Microsoft YaHei;">
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
                <div class="cb"></div>
				<div id="add_form" style=" display:none; position: relative;">
					<div style="float:left; width:400px; line-height:40px; ">
						<input type="radio" id="add_rss_radio" name="isRss" value="true" checked="checked"
							   onclick="$('#add_rss_form').attr('style','display:block;');$('#add_web_form').attr('style','display:none;');"  />
						<span style="font-size:16px; margin-right: 24px;">Rss</span>
						<input type="radio" id="add_web_radio" name="isRss" value="false"
							   onclick="$('#add_web_form').attr('style','display:block;');$('#add_rss_form').attr('style','display:none;');"/>
						<span style="font-size:16px;">Web</span>
						<div id="add_rss_form" >
							<form id="try_rss_form" action="<c:url value='/addResource.html' />" method="post">
								<div>资源名：<input class="w80" name="name" type="text" /></div>
								<div>rss地址：<input id="rss_url" class="w240" name="url" type="text" /></div>
								<input type="hidden" name="isRss" value="true" />
								<div class="fl ml20"><input id="btn_try_rss" type="button" onclick="javascript:tryRss();" value="try" class="signup_btn" /></div>
								<div class="fl"><input type="submit" value="添加" class="signup_btn" /></div>
							</form>
						</div>
						<div id="add_web_form" style="display: none;">
							<form id="try_web_form" action="<c:url value='/addResource.html' />" method="post">
								<div>资源名：<input class="w80" name="name" type="text" /></div>
								<div>url：<input id="web_url" class="w240" name="url" type="text" /></div>
								<div>xpath:<input id="web_xpath" class="w240" name="relXpath" type="text" /></div>
								<div><input class="fl ml20" id="btn_try_web" type="button" onclick="javascript:tryWeb();" value="try" class="signup_btn" /></div>
								<div><input class="fl ml20" type="submit" value="添加" class="signup_btn" /></div>
							</form>
						</div>
					</div>
					<div style="float:left;">
						<div id="name_view" class="mb10"></div>
						<div id="list_view"></div>
					</div>
				</div>
				<div class="cb"></div>
				<div style="margin:20px 20px 0px 0px; border-bottom:1px dashed #bbb;">
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

    function tryRss(){
		if($.trim($("#rss_url").val()).length < 1){
			alert("请输入url地址！");
			return;
		}
		$("#btn_try_rss").attr('disabled',"true");
		$("#btn_try_rss").attr('value','tring...');

		var loading = '<img class="mt50" src="img/loading.gif" />';
		$("#list_view").append(loading);
        var formData = $('#try_rss_form').serialize();

        $.ajax({
            type : "POST",
            dataType : "html",
            data : formData,
            async:true,
            url : "tryRss.html",
            success : function(data){
				$("#btn_try_rss").removeAttr("disabled");
				$("#btn_try_rss").attr('value','try');
				jsonObj = eval("("+data+")");
				if(jsonObj.isSuccess==true){
					$("#name_view").html("");
					$("#name_view").append("<a class='a_link_blue f16' href='"+jsonObj.link+"' >" + jsonObj.title + "</a>");
					var itemList =  jsonObj.itemList;
					$("#list_view").html("");
					var length = (itemList.length<=12)?itemList.length:12;
					for (var i=0;i<itemList.length;i++){
						if(i<length){
							$("#list_view").append("<li class='mb5'><a id='item_view"+"_"+i+
							"' class='a_link' target='_blank' href="+itemList[i].href+" >"+itemList[i].text+"</a></li>");
						}
					}
				}else{
					$("#name_view").html("");
					$("#name_view").append("<a class='a_link_blue f16' href='"+jsonObj.link+"' >" + jsonObj.link + "</a>");
				}

			}
        });
    }

	function tryWeb(){
		if($.trim($("#web_url").val()).length < 1){
			alert("请输入url地址！");
			return;
		}
		if($.trim($("#web_xpath").val()).length < 1){
			alert("请输入xpath地址！");
			return;
		}
		$("#btn_try_web").attr('disabled',"true");
		$("#btn_try_web").attr('value','tring...');

		$("#list_view").html("");
		var loading = '<img class="mt50" src="img/loading.gif" />';
		$("#list_view").append(loading);
		var formData = $('#try_web_form').serialize();

		$.ajax({
			type : "POST",
			dataType : "html",
			data : formData,
			async:true,
			url : "tryWeb.html",
			success : function(data){
				$("#btn_try_web").removeAttr("disabled");
				$("#btn_try_web").attr('value','try');
				jsonObj = eval("("+data+")");
				if(jsonObj.isSuccess==true){
					$("#name_view").html("");
					$("#name_view").append("<a class='a_link_blue f16' target='_blank' href='"+$("#web_url").val()+"' >" + $("#web_url").val() + "</a>");
					var itemList = jsonObj.itemList;
					$("#list_view").html("");
					var length = (itemList.length<=12)?itemList.length:12;
					for (var i=0;i<itemList.length;i++){
						if(i<length){
							$("#list_view").append("<li class='mb5'><a id='item_view"+"_"+i+
							"' class='a_link' target='_blank' href="+itemList[i].href+" >"+itemList[i].text+"</a><li>");
						}
					}
				}else{
					$("#name_view").html("");
					$("#name_view").append("<a class='a_link_blue f16' href='"+jsonObj.link+"' >" + jsonObj.link + "</a>");
				}

			}
		});
	}

    function submitForm(formId, formAction) {
        var formData = $('#' + formId).serialize();

        $.ajax({
            type : "POST",
            dataType : "html",
            data : formData,
            async:true,
            url : formAction,
            success : function(data) {

            }
        });
    }


	/* 显示新闻 */
	function showNews(targetIndex,itemIndex){
		if(pointedItem!=null){
			$(pointedItem).attr("style","line-height:20px;");
		}else{
			/* $("content").attr("style","width:1000px;"); */
		}
		pointedItem = $("#item_"+(targetIndex+1)+"_"+itemIndex);
		$("#item_"+(targetIndex+1)+"_"+itemIndex).attr("style","line-height:20px; font-weight: bold;");
		$("#left_panel").attr("style","width:370px;");
		$("#page_switch").attr("style","display:block; width:100%");

		trunBlockHide();
		groupIndex = Math.floor(targetIndex/2);
		trunGroupShow(groupIndex);

		$("#news_panel").html("");
		var titleDiv = "<div style='margin:0 auto; font-size:20px; font-weight: bold; margin-bottom:30px;'><a target='_blank' class='a_link color_blue' href='"+targetItems[targetIndex][itemIndex].linkUrl+"' >"+targetItems[targetIndex][itemIndex].title+"</a><div>";
		$("#news_panel").append(titleDiv,targetItems[targetIndex][itemIndex].description.htmlCode);
		$("#news_panel").attr("style","width:530px; display:block;");

	}
  </script>
</html>

