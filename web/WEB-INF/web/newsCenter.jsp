<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>NewsMetro ——你最懂自己</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/base-min.css">
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="shortcut icon" href="img/newsmetro_logo_blue_16.ico" type="image/x-icon" />
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	
  </head>
  
  <body style="font-family:Microsoft YaHei;">
  	<div class="container cf" >
		<jsp:include page="header.jsp"></jsp:include>
		<div id="content" class="content" >
			<div >
				<div class="p10">
					<div class="fl">
						<span>
							<a href="/userHome.html"><img height="48px;" src="<c:out value='${sessionScope.user.photo}' />" /></a>
						</span>
					</div>
					<div class="fl ml10" style="margin-top:20px;">
						<span class="f20" >
							<c:out value="${sessionScope.user.name }"></c:out>的新闻中心
						</span>
					</div>
					<div class="fl ml30">
						<span><a class="a_link"
							style="line-height:76px; font-size: 16px;"
							href="<c:url value="/myTargetList.html"/>">管理</a>
						</span>
					</div>
					<div class="fl">
						<a href="javascript:void(0);" id="add_res" class="a_link_guan"
							style="line-height:70px; font-size: 36px; font-weight: bold;">+</a>
					</div>
                    <div class="cb"></div>
                    <div id="add_form" style="line-height:40px; display:none; position: relative;">
                        <input type="radio" id="add_rss_radio" name="isRss" value="true" checked="checked"
                               onclick="$('#add_rss_form').attr('style','display:block;');$('#add_web_form').attr('style','display:none;');"  />
                        <span style="font-size:16px; margin-right: 24px;">Rss</span>
                        <input type="radio" id="add_web_radio" name="isRss" value="false"
                               onclick="$('#add_web_form').attr('style','display:block;');$('#add_rss_form').attr('style','display:none;');"/>
                        <span style="font-size:16px;">Web</span>
                        <div id="add_rss_form" >
                            <form action="<c:url value='/addResource.html' />" method="post">
                                <div>资源名：<input class="w80" name="name" type="text" /></div>
                                <div>rss地址：<input class="w240" name="url" type="text" /></div>
                                <input type="hidden" name="isRss" value="true" />
                                <div><input type="submit" value="添加" class="signup_btn" /></div>
                            </form>
                        </div>
                        <div id="add_web_form" style="display: none;">
                            <form action="<c:url value='/addResource.html' />" method="post">
                                <div>资源名：<input class="w80" name="name" type="text" /></div>
                                <div>url：<input class="w240" name="url" type="text" /></div>
                                <div>xpath:<input class="w240" name="relXpath" type="text" /></div>
                                <div><input type="hidden" name="isRss" value="false" /></div>
                                <div><input type="submit" value="添加" class="signup_btn" /></div>
                            </form>
                        </div>
                        <div style="margin:20 20 0 0; border-bottom:1px dashed #bbb;"></div>
                    </div>
					<div class="cb"></div>
				</div>
				<div id="splite" class="cb"></div>
				<div id="left_panel" class="fl" style="width:800px;">
					<div id="page_switch" style="display:none;">
						<a class="fl ml30 a_link color_blue" href="javascript:last_Group();">&lt;&lt;上一组</a>
						<span id="page_value" class="fl" style="margin-left:80px;" ></span>
						<a class="fr mr30 a_link color_blue" href="javascript:next_Group();">下一组&gt;&gt;</a>
					</div>
					<div id="target_panel" class="fl"></div>
				</div>
				<div id="news_panel" class="fl" style="width:530px; display: none;">
					
				</div>
				
			</div >
			
		</div>
	</div>
	<!-- <div class="m10" >
		<div class="fl f16" id="name_1"></div>
			<div class="fl mt5 ml10 color_gray" id="description_1"></div>
			<div class="cb mt5 mb10"><hr width="200px" /></div>
			<div class="a_link ">
				<ul class="fl" id="list_1">
					<li><img class="mt50" src="img/loading.gif" />
					</li>
				</ul>
			</div>
		</div> -->
  </body>
  <script type="text/javascript">
  		var groupIndex = 0;
  		var groupNum = 0;
  		var targetNum = 0;
  		var pointedItem = null;
        var targetList = null;
		var targetItems=new Array();
       for(var i=0;i<50;i++){
          targetItems[i]=new Array();
       }
       /* 获取目标点列表*/
		$(document).ready(function() {
			$.ajax({
				type : "POST",
				url : "getTargetPointList.html",
				success : function(data) {
					var panel = $("#target_panel");
					var i = 1;
					for(t in data){
						var block = document.createElement("div");
						block.setAttribute("id", "block_"+i);
						block.setAttribute("class", "m10 fl");
						panel.append(block);
						var name = '<div class="fl f16" id="name_'+i+'"></div>';
						var des = '<div class="fl mt5 ml10 color_gray w250" id="description_'+i+'"></div>';
                        var md5 = '<input type="hidden" id="md5_'+i+'" value="'+data[t].md5+'" />';
                        var target_id = '<input type="hidden" id="target_id_'+i+'" value="'+data[t].id+'" />';
						var hr = '<div class="cb mt5 mb10"><hr width="200px" /></div>';
						var ul = '<div style="width:340px;"><ul class="fl" id="list_'+i+'"><li><img class="mt50" src="img/loading.gif" /></li></ul></div>';
						$("#block_"+i).append(target_id,md5,name,des,hr,ul);
						$("#name_"+i).append("<a class='a_link_blue' href='javascript:void(0);' >" + data[t].name + "</a>");
						/*$("#block_"+i).after('<div class="cb h30"></div>');  */
                        if(i%2==0&&i!=0){
							$("#block_"+i).after("<div class='cb'></div>");
						}
						i++;
						targetNum++;
					}
                    targetList = data;
					for(var i=0;i<data.length;i++){
						getTargetPoint(data[i].isRss,i+1);
					}

					groupNum = Math.ceil(targetNum/2);
				}
			});
//            for(var i=0;i<targetNum;i++){
//                window.setInterval("checkTargetUpdate("+i+","+$("#target_id_"+i).value+","+$("#md5_"+i).value+")",30000);
//            }
		});
		/* 获取目标点内容 */
		function getTargetPoint(isRss,index){
			if(isRss==1){
				$.ajax({
					type : "POST",
					url : "getResource.html?isRss=1&id="+targetList[index-1].id,
					success : function(data) {
						$("#name_"+index).html("");
						$("#name_"+index).append("<a class='a_link_blue' href='"+data.link+"' >" + targetList[index-1].name + "</a>");
                        $("#description_"+index).html("");
                        $("#description_"+index).append("<span>" + data.description + "</span>");
						var itemList = data.itemList;
						$("#list_"+index).html("");
						var length = (itemList.length<=12)?itemList.length:12;
						for (var i=0;i<itemList.length;i++){
							if(i<length){
								$("#list_"+index).append("<li class='mb5'><a id='item_"+index+"_"+i+
                                        "' class='a_link' style='line-height:20px;' href='javascript:showNews("+(index-1)+","+i+");' >"+itemList[i].title+"</a><li>");
							}
							targetItems[index-1][i]=itemList[i];
						}
					}
				});
			}else{
				$.ajax({
					type : "POST",
					url : "getResource.html?isRss=0&id="+targetList[index-1].id,//+"&url="+url+"&relXpath="+target.relXpath+"&absXpath="+target.absXpath
					success : function(data) {
						$("#name_"+index).html("");
						$("#name_"+index).append("<a class='a_link_blue' href='"+data.link+"' >" + targetList[index-1].name + "</a>");
						$("#description_"+index).append("<span>" + data.description + "</span>");
						var itemList = data.itemList;
						$("#list_"+index).html("");
						var length = (itemList.length<=12)?itemList.length:12;
						for (var i=0;i<itemList.length;i++){
							if(i<length){
								//$("#list_"+index).append("<li class='mb5'><a id='item_"+index+"_"+i+"' class='a_link' style='line-height:20px;' href='javascript:showNews("+(index-1)+","+i+");' >"+itemList[i].title+"</a><li>");
								$("#list_"+index).append("<li class='mb5'><a id='item_"+index+"_"+i+"' class='a_link' style='line-height:20px;' href='"+itemList[i].href+"' target='_blank' >"+itemList[i].text+"</a><li>");
							}
							targetItems[index-1][i]=itemList[i];
						}
                        window.setInterval("checkTargetUpdate("+index+","+targetList[index-1].id+")",30000);
					}
				});
			}
		};
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

        function checkTargetUpdate(index,target_id){
            var old_md5 = $("#md5_"+index).attr("value");
            $.ajax({
                type : "POST",
                url : "getResourceMd5.html?id="+target_id,
                success : function(data) {
                    if(data.md5!=old_md5&&$("#has_new_"+index).length==0){
                        $("#name_"+index).append("<a style='color:red; font-size:8px; margin-left:10px;' href='javascript:void(0);' onclick='getTargetPoint(0,"+index+");'>更新"+
                                "<span class='alertLabel' style='position:relative; font-weight:bold;' id='has_new_"+index+"' >*</span></a>");
                    }
                    $("#md5_"+index).attr("value",data.md5);
                }
            });
        }

		/* 点击添加 */
		$('#add_res').click(function(){
			$('#add_form').toggle(0);
		});
		
		$(document).dblclick(function(){
			trunBlockShow();
			$("#page_switch").attr("style","display:none;");
			$("#news_panel").attr("style","display:none");
			$("#left_panel").attr("style","width:800px;display:none;");
			$("#left_panel").attr("style","width:800px;display:block;");
		});
		
		function trunBlockHide(){
			for(var i=0;i<targetNum;i++){
				$("#block_"+(i+1)).attr("style","display:none;");
			}
		}
		
		function trunBlockShow(){
			for(var i=0;i<targetNum;i++){
				$("#block_"+(i+1)).attr("style","display:block;");
			}
		}
		
		function trunGroupShow(index){
			for(var i=(index)*2;i<(index+1)*2;i++){
				$("#block_"+(i+1)).attr("style","display:block;");
			}
			$("#page_value").html((groupIndex+1)+'/'+groupNum);
		}
		
		function last_Group(){
			if(groupIndex>0){
				trunBlockHide();
				groupIndex--;
				$("#block_"+(groupIndex*2+1)).attr("style","display:block;");
				$("#block_"+(groupIndex*2+2)).attr("style","display:block;");
				$("#page_value").html((groupIndex+1)+'/'+groupNum);
			}
		}
		
		function next_Group(){
			if(groupIndex<(groupNum-1)){
				trunBlockHide();
				groupIndex++;
				$("#block_"+(groupIndex*2+1)).attr("style","display:block;");
				$("#block_"+(groupIndex*2+2)).attr("style","display:block;");
				$("#page_value").html((groupIndex+1)+'/'+groupNum);
			}
		}

        $(document).bind("selectstart",function(){return false;});
	</script>
</html>
